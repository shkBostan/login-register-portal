package ir.shkbostan.login.portal.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Centralized Spring Security configuration for the Login/Register portal.
 *
 * <p>The configuration enforces stateless REST security, exposes a BCrypt password encoder,
 * and enables CORS so a React (or any JS) frontend can call the API.</p>
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final String FRONTEND_ORIGIN = "http://localhost:3000";

    /**
     * Defines the primary HTTP security chain.
     *
     * @param http HTTP security builder
     * @return configured {@link SecurityFilterChain}
     * @throws Exception configuration errors
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(
                                "/api/register",
                                "/api/login",
                                "/api/users",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Placeholder for future JWT filter; using HTTP basic ensures protected routes require credentials.
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * BCrypt encoder bean for password hashing.
     *
     * @return password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS configuration allowing the React dev server to communicate with the API.
     *
     * @return cors configuration source
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(FRONTEND_ORIGIN));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Simple in-memory user store to authenticate protected endpoints while
     * application-level JWT/session support is being implemented.
     *
     * @param passwordEncoder encoder used to hash the demo user's password
     * @return user details service
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails apiClient = User.withUsername("api-client")
                .password(passwordEncoder.encode("api-secret"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(apiClient);
    }
}
