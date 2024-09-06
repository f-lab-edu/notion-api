package com.example.notion_api.exception;

public class CustomJsonMappingException extends RuntimeException {
    public CustomJsonMappingException(String message) {
        super(message);
    }

    public CustomJsonMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
