package org.example.bookreservationapi.security;

import org.example.bookreservationapi.employee.entity.EmployeeEntity;
import org.example.bookreservationapi.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(EmployeeService employeeService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String token) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<EmployeeEntity> employee = employeeService.findByUsername(request.username());

        if (employee.isEmpty() || !passwordEncoder.matches(request.password(), employee.get().getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }

        String token = jwtService.generateToken(employee.get().getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
