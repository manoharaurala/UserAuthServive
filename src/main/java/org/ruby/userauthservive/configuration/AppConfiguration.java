package org.ruby.userauthservive.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfiguration {

  @Bean
  public BCryptPasswordEncoder createBCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
