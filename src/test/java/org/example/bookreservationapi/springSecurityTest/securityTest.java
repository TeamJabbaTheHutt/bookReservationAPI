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
    void loginPage_isAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void validUser_canAuthenticate() throws Exception {
        mockMvc.perform(formLogin()
                        .user("employee1")
                        .password("pw"))
                .andExpect(authenticated());
    }

    @Test
    void invalidUser_cannotAuthenticate() throws Exception {
        mockMvc.perform(formLogin()
                        .user("employee1")
                        .password("fwefewfewfe"))
                .andExpect(unauthenticated());
    }

    @Test
    void successfulLogin_redirectsToHome() throws Exception {
        mockMvc.perform(formLogin()
                        .user("employee1")
                        .password("pw"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void failedLogin_redirectsToLoginWithError() throws Exception {
        mockMvc.perform(formLogin()
                        .user("employee1")
                        .password("josfnofnes"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }
}
