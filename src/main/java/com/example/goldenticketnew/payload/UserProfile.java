package com.example.goldenticketnew.payload;


import com.example.goldenticketnew.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String image;

    public UserProfile(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        if(user.getName() !=null) this.name = user.getName();
        if(user.getImage()!=null) this.image = user.getImage();
    }
}
