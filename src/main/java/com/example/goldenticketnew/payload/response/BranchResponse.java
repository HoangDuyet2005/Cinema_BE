package com.example.goldenticketnew.payload.response;

import com.example.goldenticketnew.dtos.BillDto;
import com.example.goldenticketnew.model.Branch;
import com.example.goldenticketnew.repository.IRoomRepository;
import com.example.goldenticketnew.utils.BeanUtils;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BranchResponse {
    private int id;
    private String imgURL;
    private String name;
    private String address;
    private String phoneNo;

    private List<CusRoom> rooms;

    public BranchResponse(Branch branch) {
        this.id = branch.getId();
        this.imgURL = branch.getImgURL();
        this.name = branch.getName();
        this.address = branch.getAddress();
        this.phoneNo = branch.getPhoneNo();
        IRoomRepository roomRepository = BeanUtils.getBean(IRoomRepository.class);
        this.rooms = roomRepository.findAllByBranchId(branch.getId()).stream().map(CusRoom::new).collect(Collectors.toList());
    }
}
