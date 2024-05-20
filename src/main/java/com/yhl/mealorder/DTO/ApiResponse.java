package com.yhl.mealorder.DTO;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final String status;
    private final T data;

    private ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static  <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(Status.SUCCESS.code, data);
    }

    public static <T> ApiResponse<T> failure(T data) {
        return new ApiResponse<>(Status.FAIL.code, data);
    }

    private enum Status {
        SUCCESS("0"), FAIL("-1");
        private final String code;

        Status(String code) {
            this.code = code;
        }
    }
}
