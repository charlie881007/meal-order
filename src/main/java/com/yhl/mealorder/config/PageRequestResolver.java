package com.yhl.mealorder.config;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
public class PageRequestResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.equals(parameter.getParameterType());
    }

    @Override
    public Pageable resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String page = webRequest.getParameter("page");
        String size = webRequest.getParameter("size");
        String sort = webRequest.getParameter("sort");
        boolean isAsc = webRequest.getParameter("asc") == null || Boolean.parseBoolean(webRequest.getParameter("asc"));


        int pageNum = (page != null) ? Integer.parseInt(page) : 0;
        int pageSize = (size != null) ? Integer.parseInt(size) : 10;
        String sortBy = sort != null ? sort : "id";

        return PageRequest.of(pageNum, pageSize, Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
    }
}
