package com.mosesomondi.SocialMediaAnalyticsAPI.service;

import com.mosesomondi.SocialMediaAnalyticsAPI.dto.UserDTO;

public interface SocialMediaService {
    UserDTO saveUser(UserDTO userDTO);

    UserDTO findUserById(Long userId);

}
