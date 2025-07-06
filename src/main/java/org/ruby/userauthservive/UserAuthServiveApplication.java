package org.ruby.userauthservive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;


@Import(OAuth2AuthorizationServerConfiguration.class)
@SpringBootApplication(exclude = OAuth2AuthorizationServerAutoConfiguration .class )
public class UserAuthServiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserAuthServiveApplication.class, args);
  }
}
