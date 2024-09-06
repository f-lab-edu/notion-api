package com.example.notion_api.exception;

import com.example.notion_api.api.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Api<String>> handleUserNotFoundException(UserNotFoundException ex) {
        Api<String> apiResponse = Api.<String>builder()
                .resultCode("USER_NOT_FOUND")
                .resultMessage(ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPageTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Api<String>> handleInvalidPageTypeException(InvalidPageTypeException ex) {
        Api<String> apiResponse = Api.<String>builder()
                .resultCode("INVALID_PAGE_TYPE")
                .resultMessage("잘못된 페이지 타입: " + ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

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

    @ExceptionHandler(CustomJsonMappingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Api<String>> handleCustomJsonMappingException(CustomJsonMappingException ex) {
        Api<String> apiResponse = Api.<String>builder()
                .resultCode("JSON_MAPPING_ERROR")
                .resultMessage("JSON 매핑 오류: " + ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
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

