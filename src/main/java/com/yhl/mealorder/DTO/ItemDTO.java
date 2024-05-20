package com.yhl.mealorder.DTO;

import lombok.Data;


import java.math.BigDecimal;

@Data
public class ItemDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long typeId;
    private String typeName;
    private String description;
}



