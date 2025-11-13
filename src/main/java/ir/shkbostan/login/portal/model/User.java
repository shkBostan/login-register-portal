package ir.shkbostan.login.portal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Persistent entity representing a user within the Login Register Portal.
 *
 * <p>Captures the identity and authentication-relevant attributes for each user.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    /**
     * Returns the database identifier.
     *
     * @return unique user id
     */
    public Long getId() {
        return id;
    }

    /**
     * Assigns the database identifier.
     *
     * @param id unique user id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the user's display name.
     *
     * @return full name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the user's display name.
     *
     * @param name full name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the unique email address.
     *
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the unique email address.
     *
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the hashed password.
     *
     * @return password hash
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates the hashed password.
     *
     * @param password password hash
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

