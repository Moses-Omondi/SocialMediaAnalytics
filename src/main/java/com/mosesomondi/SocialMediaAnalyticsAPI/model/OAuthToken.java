package com.mosesomondi.SocialMediaAnalyticsAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OAuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String provider; // e.g., twitter, instagram, facebook
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
