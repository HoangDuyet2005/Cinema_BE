package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.model.BillFood;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BillFoodDto {
    private Integer id;
    private Integer foodId;
    private String foodName;
    private String imageUrl;
    private Integer quantity;
    private Double price;

    public BillFoodDto(BillFood bf) {
        if (bf != null) {
            this.id = bf.getId();
            if (bf.getFoodItem() != null) {
                this.foodId = bf.getFoodItem().getId();
                this.foodName = bf.getFoodItem().getName();
                this.imageUrl = bf.getFoodItem().getImageUrl();
            }
            this.quantity = bf.getQuantity();
            this.price = bf.getPrice();
        }
    }
}