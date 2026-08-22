package com.example.goldenticketnew.payload.resquest;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BranchRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String address;
    @NotBlank
    private String phoneNo;
    @NotBlank
    private String imgURL;
}
