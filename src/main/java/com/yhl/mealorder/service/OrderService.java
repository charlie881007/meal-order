package com.yhl.mealorder.service;

import com.yhl.mealorder.DTO.OrderDTO;
import com.yhl.mealorder.entity.Order;
import com.yhl.mealorder.repository.OrderRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDTO> getAllOrders(Pageable pageRequest) {
        return orderRepository.findAll(pageRequest).stream().map(Order::toDTO).toList();
    }

    public List<OrderDTO> getByParams(Order.Status status, LocalDateTime startTime, Pageable pageRequest) {
        //
        if (null != status && null != startTime) {
            return orderRepository.findByStatusAndCreateTimeAfter(status, startTime, pageRequest).stream().map(Order::toDTO).toList();
        }

        if (null != status) {
            return orderRepository.findByStatus(status, pageRequest).stream().map(Order::toDTO).toList();
        }


        return orderRepository.findByCreateTimeAfter(startTime, pageRequest).stream().map(Order::toDTO).toList();
    }
}
