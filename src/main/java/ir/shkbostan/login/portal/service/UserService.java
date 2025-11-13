package ir.shkbostan.login.portal.service;

import ir.shkbostan.login.portal.dto.UserRegistrationRequest;
import ir.shkbostan.login.portal.model.User;
import ir.shkbostan.login.portal.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Business layer component handling user lifecycle operations.
 *
 * <p>Coordinates between DTOs, entities, and repositories for user management.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Creates a service instance backed by a repository.
     *
     * @param userRepository persistence gateway for user entities
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user based on the provided input.
     *
     * @param request encapsulates registration data
     * @return a persisted {@link User} instance
     */
    public User register(UserRegistrationRequest request) {
        // TODO: implement registration logic
        return new User();
    }
}

