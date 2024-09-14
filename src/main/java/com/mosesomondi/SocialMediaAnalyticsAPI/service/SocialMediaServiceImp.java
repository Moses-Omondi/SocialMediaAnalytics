package com.mosesomondi.SocialMediaAnalyticsAPI.service;

import com.mosesomondi.SocialMediaAnalyticsAPI.dto.UserDTO;
import com.mosesomondi.SocialMediaAnalyticsAPI.model.User;
import com.mosesomondi.SocialMediaAnalyticsAPI.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SocialMediaServiceImp implements SocialMediaService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SocialMediaServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
         User user = new User();
         user.setUsername(userDTO.getUsername());
         user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
         user.setEmail(userDTO.getEmail());

         User savedUser = userRepository.save(user);

        return new UserDTO(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }

    @Override
    public UserDTO findUserById(Long userId) {
        User savedUser = userRepository.findById(userId).orElseThrow();

        UserDTO savedUserDTO = new UserDTO();

        savedUserDTO.setUserId(savedUser.getUserId());
        savedUserDTO.setUsername(savedUser.getUsername());
        savedUserDTO.setEmail(savedUser.getEmail());

        return savedUserDTO;
    }

}
