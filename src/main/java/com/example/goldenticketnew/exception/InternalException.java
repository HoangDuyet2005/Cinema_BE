package com.example.goldenticketnew.exception;


import com.example.goldenticketnew.enums.ResponseCode;

public class InternalException extends RuntimeException {

    private final ResponseCode responseCode;

    public InternalException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return this.responseCode;
    }

    public String getMessage() {
        return this.responseCode == null ? "unknown" : this.responseCode.getMessage();
    }

    public int getCode() {
        return this.responseCode == null ? 1 : this.responseCode.getCode();
    }

}
