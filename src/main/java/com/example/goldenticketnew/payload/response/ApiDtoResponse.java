package com.example.goldenticketnew.payload.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiDtoResponse<T> {

    private Boolean status;

    private  Optional<T> content;

}
