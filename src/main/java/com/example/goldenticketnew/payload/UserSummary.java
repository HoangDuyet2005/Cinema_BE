package com.example.goldenticketnew.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String image;
    private String role;
}
