package com.example.notion_api.exception;

public class InvalidPageTypeException extends RuntimeException {
    public InvalidPageTypeException(String message) {
        super(message);
    }
}
