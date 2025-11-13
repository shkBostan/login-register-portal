package ir.shkbostan.login.portal.repository;

import ir.shkbostan.login.portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}

