package org.ruby.userauthservive.controllers;

import org.ruby.userauthservive.dtos.LoginRequestDto;
import org.ruby.userauthservive.dtos.SignupRequestDto;
import org.ruby.userauthservive.dtos.UserDto;
import org.ruby.userauthservive.models.Token;
import org.ruby.userauthservive.models.User;
import org.ruby.userauthservive.services.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final IAuthService authService;

  public AuthController(IAuthService authService) {
    this.authService = authService;
  }

  // Signup endpoint
  @PostMapping("/signup")
  public UserDto signup(@RequestBody SignupRequestDto signupRequestDto) {
    User user =
        authService.signup(
            signupRequestDto.getName(),
            signupRequestDto.getEmail(),
            signupRequestDto.getPassword(),
            signupRequestDto.getPhoneNumber());
    return from(user);
  }

  // Login endpoint
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
    Token token = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    return new ResponseEntity<>(token.getValue(), HttpStatus.OK);
  }

  @GetMapping("/validate/")
  public UserDto validateToken(@RequestHeader("Authorization") String tokenValue) {
    System.out.println("Validating token: " + tokenValue);
    User user = authService.validateToken(tokenValue);
    return from(user);
  }

  private UserDto from(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setEmail(user.getEmail());
    userDto.setName(user.getName());
    return userDto;
  }
}
