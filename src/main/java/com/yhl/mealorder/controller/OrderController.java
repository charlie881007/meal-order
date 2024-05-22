package com.yhl.mealorder.controller;

import com.yhl.mealorder.DTO.ItemDTO;
import com.yhl.mealorder.DTO.OrderDTO;
import com.yhl.mealorder.entity.Order;
import com.yhl.mealorder.service.ItemService;
import com.yhl.mealorder.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> getAllItems(Order.Status status, LocalDateTime startTime, Pageable pageRequest) {
        if (null == status && null == startTime) {
            return orderService.getAllOrders(pageRequest);
        }

        return orderService.getByParams(status, startTime, pageRequest);
    }

}