package com.yhl.mealorder.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreationDTO {
    private Long userId;
    private String phoneNum;
    private List<OrderItemDTO> orderItems;


    @Data
    public static class OrderItemDTO {
        private Long itemId;
        private Integer quantity;
    }
}
