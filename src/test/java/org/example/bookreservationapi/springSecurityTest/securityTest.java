package org.example.bookreservationapi.springSecurityTest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@SpringBootTest
@AutoConfigureMockMvc
public class securityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginEndpoint_requiresAuth() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void validUser_canAuthenticate() throws Exception {
        mockMvc.perform(formLogin("/api/login")
                        .user("employee1")
                        .password("pw"))
                .andExpect(status().isNoContent());
    }

    @Test
    void invalidUser_cannotAuthenticate() throws Exception {
        mockMvc.perform(formLogin("/api/login")
                        .user("employee1")
                        .password("wrong"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void successfulLogin_returns204() throws Exception {
        mockMvc.perform(formLogin("/api/login")
                        .user("employee1")
                        .password("pw"))
                .andExpect(status().isNoContent());
    }

    @Test
    void failedLogin_returns401() throws Exception {
        mockMvc.perform(formLogin("/api/login")
                        .user("employee1")
                        .password("wrong"))
                .andExpect(status().isUnauthorized());
    }
}