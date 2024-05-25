package com.yhl.mealorder.repository;

import com.yhl.mealorder.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(Order.Status status, Pageable pageRequest);

    List<Order> findByCreateTimeAfter(LocalDateTime createTime, Pageable pageRequest);

    List<Order> findByStatusAndCreateTimeAfter(Order.Status status, LocalDateTime createTime, Pageable pageRequest);

    Optional<Order> findByIdAndStatus(double Long, Order.Status status);
}
