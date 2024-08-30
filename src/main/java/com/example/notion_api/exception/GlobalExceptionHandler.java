package com.example.notion_api.exception;

import com.example.notion_api.api.Api;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(org.apache.tomcat.util.http.fileupload.FileUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Api<String>> handleFileUploadException(org.apache.tomcat.util.http.fileupload.FileUploadException ex) {
        Api<String> apiResponse = Api.<String>builder()
                .resultCode("FILE_UPLOAD_ERROR")
                .resultMessage("파일 업로드 실패 : " + ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileDownloadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Api<String>> handleFileDownloadException(FileDownloadException ex) {
        Api<String> apiResponse = Api.<String>builder()
                .resultCode("FILE_DOWNLOAD_ERROR")
                .resultMessage("파일 다운로드 실패 : " + ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Api<String>> handleGeneralException(Exception ex) {
        Api<String> apiResponse = Api.<String>builder()
                .resultCode("GENERAL_ERROR")
                .resultMessage("예기치 않은 에러 발생 : " + ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

