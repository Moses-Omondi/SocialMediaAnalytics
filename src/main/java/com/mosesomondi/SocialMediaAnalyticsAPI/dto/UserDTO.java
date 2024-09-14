package com.mosesomondi.SocialMediaAnalyticsAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mosesomondi.SocialMediaAnalyticsAPI.model.OAuthToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    
    Set<OAuthToken> oAuthTokenSet;

    public UserDTO(Long userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
