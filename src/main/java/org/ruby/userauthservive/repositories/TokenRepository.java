package org.ruby.userauthservive.repositories;

import org.ruby.userauthservive.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

  @Override
  public Token save(Token token);
}
