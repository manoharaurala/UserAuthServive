package org.ruby.userauthservive.repositories;

import java.util.Date;
import java.util.Optional;
import org.ruby.userauthservive.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

  @Override
  public Token save(Token token);

  // Custom query to find token by value, state ACTIVE, and expiry after now
  @Query(
      nativeQuery = true,
      value =
          """
        SELECT * FROM token t
        WHERE t.value = :value
        AND t.state = 'ACTIVE'
        AND t.expire_at > :currentDate
        """)
  Optional<Token> findActiveByValueAndNotExpired(
      @Param("value") String value, @Param("currentDate") Date currentDate);
}
