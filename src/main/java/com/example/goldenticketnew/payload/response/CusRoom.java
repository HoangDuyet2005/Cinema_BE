package com.example.goldenticketnew.payload.response;

import com.example.goldenticketnew.model.Room;
import lombok.Data;

@Data
public class CusRoom {
    private int id;
    private String name;
    private String format;
    private int capacity;
    private double totalArea;

    public CusRoom(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.format = room.getFormat();
        this.capacity = room.getCapacity();
        this.totalArea = room.getTotalArea();
        this.imgURL = room.getImgURL();
    }
    private String imgURL;

}
