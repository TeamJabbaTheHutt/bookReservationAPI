package org.example.bookreservationapi.security;

import jakarta.servlet.http.HttpServletResponse;
import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ingen csrf
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // ingen session
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/*.html", "/*.css", "/*.js").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.spa())
//                .authorizeHttpRequests(auth -> auth
//                        // Statiske frontend-filer
//                        .requestMatchers("/", "/*.html", "/*.css", "/*.js").permitAll()
//
//                        // Offentlige API-endpoints (booking-flow uden login)
//                        .requestMatchers(HttpMethod.GET, "/api/employees", "/api/employees/treatments").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/reservations").permitAll()
//
//                        // Resten kræver login
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginProcessingUrl("/api/login")
//                        .successHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_NO_CONTENT))
//                        .failureHandler((req, res, ex) -> res.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/api/logout")
//                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_NO_CONTENT))
//                )
//                .exceptionHandling(eh -> eh
//                        .authenticationEntryPoint((req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(EmployeeService employeeService) {
//        return username -> {
//            EmployeeEntity employee = employeeService.findByUsername(username)
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//            return User.builder()
//                    .username(employee.getUsername())
//                    .password(employee.getPassword())
//                    .roles("USER")
//                    .build();
//        };
//    }
//}*/
