package ir.shkbostan.login.portal.controller;

import ir.shkbostan.login.portal.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing user-related endpoints for the Login Register Portal.
 *
 * <p>Provides simple health checks and will host user management APIs.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructs the controller with its required service dependency.
     *
     * @param userService orchestrates user operations
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Simple health check endpoint.
     *
     * @return confirmation that the user service layer is responsive
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("User service is up");
    }
}

