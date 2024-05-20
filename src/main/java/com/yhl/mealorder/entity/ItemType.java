package com.yhl.mealorder.entity;

import com.yhl.mealorder.DTO.ItemTypeWithItemsDTO;
import com.yhl.mealorder.DTO.ItemTypeWithoutItemsDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Item> items = new ArrayList<>();

    public ItemType(String name) {
        this.name = name;
    }

    public ItemTypeWithoutItemsDTO toSimpleDTO() {
        ItemTypeWithoutItemsDTO dto = new ItemTypeWithoutItemsDTO();
        dto.setId(id);
        dto.setName(name);

        return dto;
    }

    public ItemTypeWithItemsDTO toDetailedDTO() {
        ItemTypeWithItemsDTO dto = new ItemTypeWithItemsDTO();
        dto.setId(id);
        dto.setName(name);

        items.forEach((item -> {
            ItemTypeWithItemsDTO.ItemDTO itemDTO = new ItemTypeWithItemsDTO.ItemDTO();
            itemDTO.setItemName(item.getName());
            itemDTO.setItemId(item.getId());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setDescription(item.getDescription());

            dto.addItem(itemDTO);
        }));
        return dto;
    }

}