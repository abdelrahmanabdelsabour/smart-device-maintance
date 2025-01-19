package com.elcmal.controller;

import com.elcmal.model.Role;
import com.elcmal.payload.request.LoginRequest;
import com.elcmal.payload.request.SignupRequest;
import com.elcmal.repository.RoleRepository;
import com.elcmal.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        
        // Ensure roles exist
        if (!roleRepository.existsByName(Role.ERole.ROLE_USER)) {
            Role userRole = new Role();
            userRole.setName(Role.ERole.ROLE_USER);
            roleRepository.save(userRole);
        }
        if (!roleRepository.existsByName(Role.ERole.ROLE_ADMIN)) {
            Role adminRole = new Role();
            adminRole.setName(Role.ERole.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }
    }

    @Test
    public void givenValidSignupRequest_whenRegister_thenReturnSuccess() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser");
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword("password123");
        Set<String> roles = new HashSet<>();
        roles.add("user");
        signupRequest.setRoles(roles);

        // when
        ResultActions response = mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", 
                        is("User registered successfully!")));
    }

    @Test
    public void givenExistingUsername_whenRegister_thenReturnError() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("existinguser");
        signupRequest.setEmail("existing@example.com");
        signupRequest.setPassword("password123");

        // Register first user
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)));

        // Try to register same username again
        ResultActions response = mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)));

        // then
        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", 
                        is("Error: Username is already taken!")));
    }

    @Test
    public void givenValidCredentials_whenLogin_thenReturnJwtToken() throws Exception {
        // given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("loginuser");
        signupRequest.setEmail("login@example.com");
        signupRequest.setPassword("password123");

        // Register user first
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("loginuser");
        loginRequest.setPassword("password123");

        // when
        ResultActions response = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username", is("loginuser")))
                .andExpect(jsonPath("$.email", is("login@example.com")));
    }

    @Test
    public void givenInvalidCredentials_whenLogin_thenReturnError() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("nonexistentuser");
        loginRequest.setPassword("wrongpassword");

        // when
        ResultActions response = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        // then
        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", 
                        is("Error: Invalid username or password")));
    }
}
