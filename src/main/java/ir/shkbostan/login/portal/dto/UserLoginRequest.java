package ir.shkbostan.login.portal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO encapsulating user login credentials.
 *
 * <p>Validation ensures email and password inputs meet basic requirements.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */
public class UserLoginRequest {

    @NotBlank
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    /**
     * Returns the login email address.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the login email address.
     *
     * @param email login email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the raw login password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the raw login password.
     *
     * @param password raw password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}


