package ir.shkbostan.login.portal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data transfer object capturing user registration details.
 *
 * <p>Validated fields ensure basic integrity for incoming registration requests.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */
public class UserRegistrationRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    /**
     * Returns the desired username.
     *
     * @return chosen username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the desired username.
     *
     * @param username chosen username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the registrant's email address.
     *
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the registrant's email address.
     *
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the plain password provided during registration.
     *
     * @return raw password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the plain password provided during registration.
     *
     * @param password raw password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

