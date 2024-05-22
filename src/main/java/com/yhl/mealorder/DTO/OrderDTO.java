package com.yhl.mealorder.DTO;

import com.yhl.mealorder.entity.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private String phoneNum;
    private Integer amount;
    private List<OrderItemDTO> orderItems;
    private LocalDateTime createTime;
    private Order.Status status;

    @Getter
    @Setter
    public static class OrderItemDTO {
        private Long itemId;
        private String name;
        private Integer quantity;
        private BigDecimal amount;
    }
}
