package org.example.bookreservationapi.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
// test
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public pages
                        .requestMatchers("/", "/index.html", "/styles.css", "/app.js").permitAll()

                        // Public API (optional)
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()


                        .requestMatchers(HttpMethod.POST, "/api/reservations").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/reservations/delete/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/reservations/edit/**").authenticated()


                        // Everything else
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .permitAll() // default login page
                )

                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails employee = User.builder()
                .username("employee1")
                .password(passwordEncoder.encode("pw"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(employee);
    }
}