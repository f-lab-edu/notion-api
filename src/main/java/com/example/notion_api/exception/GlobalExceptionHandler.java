package com.example.notion_api.exception;

import com.example.notion_api.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
@Order
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Api<String> handleIOException(IOException ex) {
        log.error("IOException 에러 발생 : ", ex);
        return Api.<String>builder()
                .resultCode("500")
                .resultMessage("서버 오류가 발생했습니다: " + ex.getMessage())
                .data(null)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Api<String> handleGeneralException(Exception ex) {
        log.error("Unexpected error 발생 : ", ex);
        return Api.<String>builder()
                .resultCode("500")
                .resultMessage("예기치 않은 오류가 발생했습니다: " + ex.getMessage())
                .data(null)
                .build();
    }
}
