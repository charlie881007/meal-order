package com.yhl.mealorder.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemTypeWithoutItemsDTO {
    private Long id;
    private String name;


    @Data
    public static class ItemDTO {
        private Long itemId;
        private String itemName;
        private BigDecimal price;
        private String description;
    }
}

