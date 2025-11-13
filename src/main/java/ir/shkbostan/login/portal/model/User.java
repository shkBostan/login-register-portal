package ir.shkbostan.login.portal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Persistent entity representing a user within the Login Register Portal.
 *
 * <p>This placeholder entity currently tracks only the generated database identifier.</p>
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
}

