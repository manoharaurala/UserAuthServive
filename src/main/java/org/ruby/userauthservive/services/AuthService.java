package org.ruby.userauthservive.services;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.ruby.userauthservive.dtos.SendEmailDto;
import org.ruby.userauthservive.exceptions.InvalidTokenException;
import org.ruby.userauthservive.exceptions.PasswordMismatchException;
import org.ruby.userauthservive.exceptions.UserAlreadyExistException;
import org.ruby.userauthservive.exceptions.UserNotSignedException;
import org.ruby.userauthservive.models.State;
import org.ruby.userauthservive.models.Token;
import org.ruby.userauthservive.models.User;
import org.ruby.userauthservive.repositories.TokenRepository;
import org.ruby.userauthservive.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private KafkaTemplate <String, String> kafkaTemplate;

  private ObjectMapper mapper = new ObjectMapper();

  public AuthService(
      UserRepository userRepository,
      TokenRepository tokenRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.tokenRepository = tokenRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public User signup(String name, String email, String password, String phoneNumber) {

    Optional<User> userOptional = userRepository.findByEmailEqualsIgnoreCase(email);
    if (userOptional.isPresent()) {
      throw new UserAlreadyExistException("User with this email already exists");
    }
    User user = new User();
    user.setName(name);
    user.setEmail(email);
    // ToDo : Use Bcrypt here
    user.setPassword(bCryptPasswordEncoder.encode(password));
    user.setPhoneNumber(phoneNumber);
    user.setCreatedAt(new Date());
    user.setLastUpdatedAt(new Date());
    user.setCreatedBy(email);
    user.setState(State.ACTIVE);
    user = userRepository.save(user);
    // Send a message to Kafka topic after user signup
    SendEmailDto sendEmailDto = new SendEmailDto();
    sendEmailDto.setSubject("Welcome to Our Ruby Service");
    sendEmailDto.setBody("Hello " + user.getName() + ",\n\nThank you for signing up!");
    sendEmailDto.setToEmail(user.getEmail());
    String sendEmailDtoJson=null;
    try {
      sendEmailDtoJson = mapper.writeValueAsString(sendEmailDto);
      kafkaTemplate.send("sendEmailEvent", sendEmailDtoJson);
    } catch (Exception e) {
      e.printStackTrace();
    }

    kafkaTemplate.send("sendEmailEvent",
            sendEmailDtoJson);
    return user;
  }

  @Override
  public Token login(String email, String password) {
    Optional<User> userOptional = userRepository.findByEmailEqualsIgnoreCase(email);
    if (userOptional.isEmpty()) {
      throw new UserNotSignedException("Please try signup first");
    }

    if (!bCryptPasswordEncoder.matches(password, userOptional.get().getPassword())) {
      throw new PasswordMismatchException("Please type correct password");
    }
    // ToDo : Generate JWT
    /* create a token and save it in the database

    */
    Token token = new Token();
    token.setUser(userOptional.get());
    token.setValue(RandomStringUtils.randomAlphanumeric(128));
    token.setState(State.ACTIVE);
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, 30);
    Date dayAfterThirtyDays = calendar.getTime();
    token.setExpire_At(dayAfterThirtyDays);
    return tokenRepository.save(token);
  }

  @Override
  public User validateToken(String tokenValue) {
    Optional<Token> optionalToken =
        tokenRepository.findActiveByValueAndNotExpired(tokenValue, new Date());
    if (optionalToken.isEmpty()) {
      // Token is not valid or expired
      throw new InvalidTokenException("Invalid token");
    }
    return optionalToken.get().getUser();
  }
}
