package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.model.Room;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RoomDto {
    private int id;
    private String name;
    private String format;
    private int capacity;
    private double totalArea;

    public RoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.format = room.getFormat();
        this.capacity = room.getCapacity();
        this.totalArea = room.getTotalArea();
        this.imgURL = room.getImgURL();
        if (room.getBranch() != null) {
            this.branch = new BranchDto(room.getBranch());
        }
    }

    private String imgURL;
    @JsonIgnore
    private BranchDto branch;
}
