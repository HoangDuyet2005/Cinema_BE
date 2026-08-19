package com.example.goldenticketnew.payload.resquest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @NotNull
    private Long id;

    private String name;

    private String image;

    private String bio;

}
