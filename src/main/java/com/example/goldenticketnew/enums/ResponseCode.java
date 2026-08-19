package com.example.goldenticketnew.enums;

public enum ResponseCode {
    SUCCESS(0, "Success"),
    FAILED(1, "Failed"),

    COMMON_ERROR(2, "Common Error"),
    INVALID_PARAM(3, "Invalid param"),

    LOGIN_FAIL(4,"Login failed"),

    //User
    USER_NOT_FOUND(101, " User not found"),
    USER_1(102,"User đã có quyền viết bài!"),
    USER_2(103,"User đã gửi yêu cầu rồi!"),
    USER_3(104,"User chưa gửi yêu cầu!"),


    //Movie
    MOVIE_NOT_FOUND(201, " Movie not found"),

    MOVIE_RELEASE_DATE_INVALID(202, " Release date have to after current date"),

    //Schedule
    SCHEDULE_NOT_FOUND(301, " Schedule not found"),

    //Schedule
    ARTICLE_NOT_FOUND(401, " Article not found"),


    //Branch
    BRANCH_NOT_FOUND(402, " Branch not found"),

    //Bill
    BILL_NOT_FOUND(501, " Bill not found"),

    //Seat
    SEAT_NOT_FOUND(601, " Seat not found"),

    //Comment not fountd
    COMMENT_NOT_FOUND(701, " Comment not found"),
    //vietnam message

    BOOKING_SEAT_EXIST(901,"Đã có người nhanh tay hơn đặt ghế, mời bạn chọn lại!"),


    ;
    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseCode valueOf(long value) {
        for (ResponseCode responseCode : ResponseCode.values()) {
            if (responseCode.getCode() == value) {
                return responseCode;
            }
        }
        return null;
    }
}