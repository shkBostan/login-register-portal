package ir.shkbostan.login.portal.dto;

/**
 * DTO representing user information returned to clients.
 *
 * <p>Omits sensitive data such as passwords while providing identification fields.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */
public class UserResponse {

    private final Long id;
    private final String name;
    private final String email;

    /**
     * Constructs a response with the supplied user information.
     *
     * @param id    unique identifier
     * @param name  display name
     * @param email email address
     */
    public UserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * @return unique identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * @return display name
     */
    public String getName() {
        return name;
    }

    /**
     * @return email address
     */
    public String getEmail() {
        return email;
    }
}


