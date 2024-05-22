package com.yhl.mealorder.exception;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException() {
        super("參數錯誤");
    }
}
