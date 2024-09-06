package com.example.notion_api.exception;

import com.example.notion_api.controller.PageController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {PageController.class})
public class PageControllerExceptionHandler {

}
