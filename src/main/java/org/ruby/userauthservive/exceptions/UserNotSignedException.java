package org.ruby.userauthservive.exceptions;

public class UserNotSignedException extends RuntimeException {
  public UserNotSignedException(String message) {
    super(message);
  }

  public UserNotSignedException(String message, Throwable cause) {
    super(message, cause);
  }
}
