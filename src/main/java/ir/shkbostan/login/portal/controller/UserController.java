package ir.shkbostan.login.portal.controller;

import ir.shkbostan.login.portal.dto.UserLoginRequest;
import ir.shkbostan.login.portal.dto.UserRegistrationRequest;
import ir.shkbostan.login.portal.dto.UserResponse;
import ir.shkbostan.login.portal.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST controller exposing user-related endpoints for the Login Register Portal.
 *
 * <p>Sample request:</p>
 *
 * <pre>
 * POST /api/register
 * {
 *   "name": "shekoofeh Bostan",
 *   "email": "shekoofeh@example.com",
 *   "password": "P@ssword123"
 * }
 *
 * Sample response (201):
 * {
 *   "id": 1,
 *   "name": "shekoofeh Bostan",
 *   "email": "shekoofeh@example.com"
 * }
 * </pre>
 *
 * <p>Similar payloads exist for login/logout endpoints for quick manual testing.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */
@RestController
@RequestMapping("/api")
@Tag(name = "User API", description = "Endpoints for registering, authenticating, and listing users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a brand-new user using JSON input.
     *
     * @param request validated registration payload
     * @return persisted user details
     */
    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new account after validating that the email address is unique.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationRequest.class),
                            examples = @ExampleObject(
                                    name = "RegistrationPayload",
                                    value = """
                                            {
                                              "name": "Shekoofeh Bostan",
                                              "email": "shekoofeh@example.com",
                                              "password": "P@ssword123"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"error\":\"Email address is already in use\"}")))
    })
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegistrationRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Authenticates a user and issues a mock session/JWT token for testing.
     *
     * <p>Sample response:</p>
     * <pre>
     * {
     *   "token": "1d5e0a1c-7d91-4e32-9a8a-2a7d8c3d0f12",
     *   "user": {
     *     "id": 1,
     *     "name": "shekoofeh Bostan",
     *     "email": "shekoofeh@example.com"
     *   },
     *   "message": "Login successful"
     * }
     * </pre>
     *
     * @param request validated login payload
     * @return authenticated user data and issued token
     */
    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user credentials",
            description = "Verifies email and password, then issues a mock token for clients to use in subsequent requests.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserLoginRequest.class),
                            examples = @ExampleObject(
                                    name = "LoginPayload",
                                    value = """
                                            {
                                              "email": "shekoofeh@example.com",
                                              "password": "P@ssword123"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                      "token": "1d5e0a1c-7d91-4e32-9a8a-2a7d8c3d0f12",
                                      "user": {
                                        "id": 1,
                                        "name": "Shekoofeh Bostan",
                                        "email": "shekoofeh@example.com"
                                      },
                                      "message": "Login successful",
                                      "issuedAt": "2025-11-14T10:05:00Z"
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"error\":\"Invalid email or password\"}")))
    })
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody UserLoginRequest request) {
        UserResponse user = userService.authenticate(request);
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("token", issueToken());
        payload.put("user", user);
        payload.put("message", "Login successful");
        payload.put("issuedAt", Instant.now().toString());
        return ResponseEntity.ok(payload);
    }

    /**
     * Invalidates the provided Authorization header (mock implementation).
     *
     * @param authorization authorization header if available
     * @return confirmation of logout
     */
    @PostMapping("/logout")
    @Operation(
            summary = "Invalidate a user session",
            description = "Accepts an optional Authorization header and returns confirmation that the token has been discarded."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User logged out",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                    {
                                      "message": "Logout successful",
                                      "tokenInvalidated": "Bearer 1d5e0a1c-7d91-4e32-9a8a-2a7d8c3d0f12"
                                    }
                                    """)))
    })
    public ResponseEntity<Map<String, String>> logout(
            @Parameter(
                    name = "Authorization",
                    description = "Bearer token issued during login",
                    required = false,
                    example = "Bearer 1d5e0a1c-7d91-4e32-9a8a-2a7d8c3d0f12"
            )
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        Map<String, String> payload = Map.of(
                "message", "Logout successful",
                "tokenInvalidated", authorization == null ? "none provided" : authorization
        );
        return ResponseEntity.ok(payload);
    }

    /**
     * Returns a list of all users - primarily for local verification.
     *
     * @return user list
     */
    @GetMapping("/users")
    @Operation(
            summary = "List registered users",
            description = "Returns every persisted user. Intended for local QA/testing only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of users",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
    })
    public ResponseEntity<List<UserResponse>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    /**
     * Converts IllegalArgumentExceptions to 400 responses with helpful messages.
     *
     * @param ex exception thrown by service layer
     * @return error payload
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", ex.getMessage(),
                "timestamp", Instant.now().toString()
        ));
    }

    /**
     * Generic fallback for uncaught exceptions to avoid leaking stacktraces.
     *
     * @param ex unexpected exception
     * @return sanitized error payload
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "An unexpected error occurred. Please try again later.",
                "timestamp", Instant.now().toString()
        ));
    }

    private String issueToken() {
        return UUID.randomUUID().toString();
    }
}