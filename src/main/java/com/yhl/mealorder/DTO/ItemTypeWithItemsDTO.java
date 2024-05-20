package com.yhl.mealorder.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemTypeWithItemsDTO extends ItemTypeWithoutItemsDTO {

    private List<ItemDTO> items = new ArrayList<>();

    public void addItem(ItemDTO itemDTO) {
        items.add(itemDTO);
    }

    @Data
    public static class ItemDTO {
        private Long itemId;
        private String itemName;
        private BigDecimal price;
        private String description;
    }
}

