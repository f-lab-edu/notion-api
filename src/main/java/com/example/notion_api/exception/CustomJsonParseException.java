package com.example.notion_api.exception;

public class CustomJsonParseException extends RuntimeException {
    public CustomJsonParseException(String message) {
        super(message);
    }
}
