package com.example.goldenticketnew.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FieldRequestError {

    public List<Error> errors;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Error {
        private String field;
        private String errorMessage;
    }
}
