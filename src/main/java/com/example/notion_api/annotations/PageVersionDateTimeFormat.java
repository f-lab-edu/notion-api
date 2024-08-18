package com.example.notion_api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageVersionDateTimeFormat {
    String pattern() default "dd.MM.yy HH:mm:ss";
}
