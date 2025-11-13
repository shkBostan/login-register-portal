package ir.shkbostan.login.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Login Register Portal Spring Boot application.
 *
 * <p>This class bootstraps the Spring context and starts the embedded server.</p>
 *
 * @author s Bostan
 * @since Nov, 2025
 */
@SpringBootApplication
public class LoginRegisterPortalApplication {

    /**
     * Starts the Login Register Portal application.
     *
     * @param args runtime arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(LoginRegisterPortalApplication.class, args);
    }
}
