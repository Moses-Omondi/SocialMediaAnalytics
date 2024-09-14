package com.mosesomondi.SocialMediaAnalyticsAPI.controller;

import com.mosesomondi.SocialMediaAnalyticsAPI.dto.UserDTO;
import com.mosesomondi.SocialMediaAnalyticsAPI.service.SocialMediaServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    @Autowired
    private SocialMediaServiceImp socialMediaService;

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {

        return socialMediaService.findUserById(id);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {

        return socialMediaService.saveUser(userDTO);
    }
}
