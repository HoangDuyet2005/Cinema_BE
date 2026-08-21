package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.model.Branch;
import lombok.Data;

@Data
public class BranchDto {
    private int id;
    private String imgURL;
    private String name;
    private String city;
    private String address;
    private String phoneNo;

    public BranchDto(Branch branch) {
        this.id = branch.getId();
        this.imgURL = branch.getImgURL();
        this.name = branch.getName();
        this.city = branch.getCity();
        this.address = branch.getAddress();
        this.phoneNo = branch.getPhoneNo();
    }
}
