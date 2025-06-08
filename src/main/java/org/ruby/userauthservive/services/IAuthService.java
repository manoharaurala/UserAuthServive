package org.ruby.userauthservive.services;

import org.ruby.userauthservive.models.Token;
import org.ruby.userauthservive.models.User;

public interface IAuthService {
  User signup(String name, String email, String password, String phoneNumber);

  Token login(String email, String password);
}
