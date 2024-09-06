package com.example.notion_api.exception;

public class FileDownloadException extends RuntimeException {
    public FileDownloadException(String message) {
        super(message);
    }
}
