package ir.shkbostan.login.portal.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration tuned for local development.
 *
 * <p>Permits unrestricted access to the application and the embedded H2 console while
 * preserving the filter chain structure for future hardening.</p>
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Configures the primary security filter chain.
     *
     * @param http security builder
     * @return configured {@link SecurityFilterChain}
     * @throws Exception in case the configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
