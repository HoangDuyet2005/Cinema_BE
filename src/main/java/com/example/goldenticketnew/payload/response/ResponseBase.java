package com.example.goldenticketnew.payload.response;

import com.example.goldenticketnew.enums.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseBase<T> {

    private int code;
    private String message;
    private T data;

    public ResponseBase(T data) {
        this.data = data;
        this.message = ResponseCode.SUCCESS.getMessage();
        this.code = ResponseCode.SUCCESS.getCode();
    }

    public ResponseBase(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public ResponseBase(T data, int code, String message) {
        this.data = data;
        this.message = message;
        this.code = code;
    }
}

