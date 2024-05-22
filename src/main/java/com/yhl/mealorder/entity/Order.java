package com.yhl.mealorder.entity;


import com.fasterxml.jackson.annotation.JsonValue;
import com.yhl.mealorder.DTO.OrderDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "order_")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String phoneNum;

    @NotNull
    private Integer amount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @NotNull
    private LocalDateTime createTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    public Order(String phoneNum, List<OrderItem> orderItems, LocalDateTime createTime, Status status) {
        this.phoneNum = phoneNum;
        this.orderItems = orderItems;
        this.createTime = createTime;
        this.status = status;

        amount = Order.calcAmount(this);
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        this.amount = Order.calcAmount(this);
    }

    public void addOrderItem(Item item, Integer quantity) {
        OrderItem orderItem = new OrderItem(this, item, quantity);
        this.orderItems.add(orderItem);
        calcAmount(this);
    }

    public OrderDTO toDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setAmount(amount);
        orderDTO.setPhoneNum(phoneNum);
        orderDTO.setCreateTime(createTime);
        orderDTO.setStatus(status);

        List<OrderDTO.OrderItemDTO> itemDTOS = new ArrayList<>();
        orderItems.forEach(item -> itemDTOS.add(item.toDTO()));
        orderDTO.setOrderItems(itemDTOS);

        return orderDTO;
    }

    public enum Status {
        IN_PROGRESS("0"), COMPLETED("1");

        private final String code;

        Status(String code) {
            this.code = code;
        }

        @JsonValue
        public String getCode() {
            return code;
        }
    }

    public static Integer calcAmount(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        if (order.getOrderItems() != null) {
            for (OrderItem orderItem : order.getOrderItems()) {
                total = total.add(orderItem.getItem().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            }
        }

        // 進位到整數位
        return total.setScale(0, RoundingMode.UP).intValue();
    }
}