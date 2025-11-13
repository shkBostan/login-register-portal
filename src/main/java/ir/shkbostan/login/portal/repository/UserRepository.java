package ir.shkbostan.login.portal.repository;

import ir.shkbostan.login.portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Data access layer abstraction for {@link ir.shkbostan.login.portal.model.User} entities.
 *
 * <p>Extends Spring Data JPA to provide CRUD operations.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Lookup a user by its unique email address.
     *
     * @param email unique email address
     * @return optional containing the matching {@link User} when present
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if an email already exists in the data store.
     *
     * @param email unique email address
     * @return {@code true} when at least one user owns the email
     */
    boolean existsByEmail(String email);
}

