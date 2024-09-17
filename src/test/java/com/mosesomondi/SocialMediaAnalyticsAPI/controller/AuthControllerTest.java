package com.mosesomondi.SocialMediaAnalyticsAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosesomondi.SocialMediaAnalyticsAPI.config.TestSecurityConfig;
import com.mosesomondi.SocialMediaAnalyticsAPI.dto.UserDTO;
import com.mosesomondi.SocialMediaAnalyticsAPI.exception.UserAlreadyExistsException;
import com.mosesomondi.SocialMediaAnalyticsAPI.exception.UserNotFoundException;
import com.mosesomondi.SocialMediaAnalyticsAPI.service.SocialMediaServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @DisplayName("Test for Creating a User - Should Return Created User.")
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

    @Test
    @DisplayName("Test for Creating an existing User - Should Return Conflicted.")
    public void testCreateExistingUser_ShouldReturnConflict() throws Exception {
        // Mocking the service response
        when(socialMediaService.saveUser(any(UserDTO.class))).thenThrow(new UserAlreadyExistsException("User already Exists."));

        // Perform a POST request to create a user that already exists
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON).with(csrf())
                .content(objectMapper.writeValueAsString(userDTO)))

                // Expect 409 Conflict status and check the exception message
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User already Exists."));
    }

    @Test
    @DisplayName("Test for Fetching a User by ID - Should Return User Details.")
    public void testGetUserId_ShouldReturnUser() throws Exception {
        // Mocking the service response
        when(socialMediaService.findUserById(1L)).thenReturn(userDTO);

        // Perform a Get request to  your controller
        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON).with(csrf()))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userDTO.getUserId()))
                .andExpect(jsonPath("$.username").value(userDTO.getUsername()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));

    }

    @Test
    @DisplayName("Test for Fetching a Non-Existing User - Should Return Not Found.")
    public void testGetUserById_ShouldReturnNotFound() throws Exception {
        // Mocking the service to throw an exception when user is not found.
        when(socialMediaService.findUserById(1L)).thenThrow(new UserNotFoundException("User not Found."));

        // Sending GET request to fetch a non-existing user.
        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON).with(csrf()))

                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not Found."));
    }
}