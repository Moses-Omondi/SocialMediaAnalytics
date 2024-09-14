package com.mosesomondi.SocialMediaAnalyticsAPI.service;

import com.mosesomondi.SocialMediaAnalyticsAPI.dto.UserDTO;
import com.mosesomondi.SocialMediaAnalyticsAPI.exception.UserAlreadyExistsException;
import com.mosesomondi.SocialMediaAnalyticsAPI.exception.UserNotFoundException;
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

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists.");
        }

        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username " + userDTO.getUsername() + " already exists.");
        }

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
        User savedUser = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with ID " + userId + " not found.")
        );

        UserDTO savedUserDTO = new UserDTO();

        savedUserDTO.setUserId(savedUser.getUserId());
        savedUserDTO.setUsername(savedUser.getUsername());
        savedUserDTO.setEmail(savedUser.getEmail());

        return savedUserDTO;
    }

}
