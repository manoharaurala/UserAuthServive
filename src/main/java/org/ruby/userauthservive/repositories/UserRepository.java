package org.ruby.userauthservive.repositories;

import java.util.Optional;
import org.ruby.userauthservive.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmailEqualsIgnoreCase(String email);
}
