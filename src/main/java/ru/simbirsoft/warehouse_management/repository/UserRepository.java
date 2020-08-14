package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.user.User;

import java.util.Optional;

/** Jpa repository for user entity */
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Returns optional of user with such username.
   *
   * @param username - username of the User
   * @return optional of user with such username
   */
  Optional<User> findUserByUsername(String username);

  /**
   * Checks if there is a user with this email address
   *
   * @param email - email of the user
   * @return true if user was founded otherwise returns false
   */
  boolean existsByEmail(String email);

  /**
   * Checks if there is a user with this username
   *
   * @param username - username of the user
   * @return true if user was founded otherwise returns false
   */
  boolean existsByUsername(String username);
}
