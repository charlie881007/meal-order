package com.yhl.mealorder.config;

import com.yhl.mealorder.entity.Order;
import com.yhl.mealorder.exception.InvalidArgumentException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OrderStatusResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Order.Status.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String status = webRequest.getParameter("status");
        // 預設全找
        if (status == null) {
            return null;
        }

        // status = 0，剛接單
        if (status.equals("0")) {
            return Order.Status.IN_PROGRESS;
        }

        // status = 1，已完成
        if (status.equals("1")) {
            return Order.Status.COMPLETED;
        }

        // status參數不明
        throw new InvalidArgumentException();
    }
}
