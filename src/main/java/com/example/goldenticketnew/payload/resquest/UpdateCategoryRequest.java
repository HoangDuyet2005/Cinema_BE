package com.example.goldenticketnew.payload.resquest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryRequest {

    private List<Long> categories;
    @NotNull
    private Long userId;
}
