package com.yhl.mealorder.config;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeArgumentResolver implements HandlerMethodArgumentResolver {

    private final DateTimeFormatter formatter;

    public LocalDateTimeArgumentResolver() {
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return LocalDateTime.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String dateTimeString = webRequest.getParameter("startTime");

        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }

        return LocalDateTime.parse(dateTimeString, this.formatter);
    }
}
