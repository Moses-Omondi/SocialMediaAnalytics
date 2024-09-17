package com.mosesomondi.SocialMediaAnalyticsAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosesomondi.SocialMediaAnalyticsAPI.config.TestSecurityConfig;
import com.mosesomondi.SocialMediaAnalyticsAPI.dto.UserDTO;
import com.mosesomondi.SocialMediaAnalyticsAPI.service.SocialMediaServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Import(TestSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SocialMediaServiceImp socialMediaService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO(1L, "JavelinMcGee", "jalengreen@gmail.com", "Password123");
    }

    @Test
    public void testCreateUser_ShouldReturnCreatedUser() throws Exception {
        // Mocking the service response
        when(socialMediaService.saveUser(any(UserDTO.class))).thenReturn(userDTO);

        // Perform a POST request to your controller
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON).with(csrf())
                .content(objectMapper.writeValueAsString((userDTO))))


                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(userDTO.getUserId()))
                .andExpect(jsonPath("$.username").value(userDTO.getUsername()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));

    }
}