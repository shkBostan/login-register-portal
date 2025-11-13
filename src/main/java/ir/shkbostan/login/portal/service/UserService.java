package ir.shkbostan.login.portal.service;

import ir.shkbostan.login.portal.dto.UserLoginRequest;
import ir.shkbostan.login.portal.dto.UserRegistrationRequest;
import ir.shkbostan.login.portal.dto.UserResponse;
import ir.shkbostan.login.portal.model.User;
import ir.shkbostan.login.portal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a service instance backed by a repository.
     *
     * @param userRepository  persistence gateway for user entities
     * @param passwordEncoder encoder for securing user passwords
     */
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user based on the provided input.
     *
     * @param request encapsulates registration data
     * @return a response representation of the persisted user
     */
    @Transactional
    public UserResponse register(UserRegistrationRequest request) {
        String email = normalizeEmail(request.getEmail());
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email address is already in use");
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    /**
     * Authenticates a user's credentials.
     *
     * @param request login payload
     * @return response describing the authenticated user
     */
    @Transactional(readOnly = true)
    public UserResponse authenticate(UserLoginRequest request) {
        String email = normalizeEmail(request.getEmail());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return toResponse(user);
    }

    /**
     * Retrieves a collection of all registered users.
     *
     * @return list of user responses
     */
    @Transactional(readOnly = true)
    public List<UserResponse> listUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}

