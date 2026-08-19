package com.example.goldenticketnew.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedBy;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auditable {
    private String createdBy;

    private String modifiedBy;

    private String createdAt;

    private String updatedAt;
}
