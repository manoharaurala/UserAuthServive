package org.ruby.userauthservive;

import org.junit.jupiter.api.Test;
import org.ruby.userauthservive.security.repositories.JpaRegisteredClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@SpringBootTest
class UserAuthServiveApplicationTests {

  @Autowired
  JpaRegisteredClientRepository registeredClientRepository;

  @Test
  void contextLoads() {}
  @Test
  public void testCase(){
    RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("oidc-client")
                .clientSecret("$2a$12$keaDq2OvC4u4KgYROL0FHOPK8Le1HKq95hXO9SStHNKW5qENxkWLW")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope("ADMIN")
                .scope("STUDENT")
                .scope("MENTOR")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        registeredClientRepository.save(oidcClient);
  }
}
