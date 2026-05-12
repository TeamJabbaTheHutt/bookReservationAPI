package org.example.bookreservationapi.security;

import jakarta.servlet.http.HttpServletResponse;
import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

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
//
//        http
//                .csrf(csrf -> csrf.disable())
//
//                .authorizeHttpRequests(auth -> auth
//
//                        // static files
//                        .requestMatchers("/", "/index.html", "/*.js", "/*.css").permitAll()
//
//                        // API public
//                        .requestMatchers("/api/**").permitAll()
//
//                        // everything else locked
//                        .anyRequest().authenticated()
//                )
//
//                .formLogin(form -> form
//                        .loginProcessingUrl("/api/login")
//                        .permitAll()
//                )
//
//                .logout(logout -> logout
//                        .logoutUrl("/api/logout")
//                        .permitAll()
//                )
//
//                .exceptionHandling(eh -> eh
//                        .authenticationEntryPoint((req, res, ex) ->
//                                res.sendError(401)
//                        )
//                );
//
//        return http.build();
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(EmployeeService employeeService) {
//
//        return username -> {
//
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
//}