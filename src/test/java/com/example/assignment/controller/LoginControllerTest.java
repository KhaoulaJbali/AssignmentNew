package com.example.assignment.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
 class LoginControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    @Test
     void testLoginSuccessful() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();

        Authentication authentication = new UsernamePasswordAuthenticationToken("testuser", "testpassword");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        String loginRequestJson = "{\"username\":\"testuser\",\"password\":\"testpassword\"}";

        MvcResult result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
                .andExpect(status().isOk())
                .andReturn();

        // Validate the response content
        String content = result.getResponse().getContentAsString();
        assert(content.equals("Login successful"));

        // Validate that authentication was set in SecurityContextHolder
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert(auth.getPrincipal().equals("testuser"));
        assert(auth.getCredentials().equals("testpassword"));
    }
}

