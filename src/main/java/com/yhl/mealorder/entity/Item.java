package com.yhl.mealorder.entity;

import com.yhl.mealorder.DTO.ItemDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @Cascade(CascadeType.ALL)
    private ItemType type;

    @NotNull
    @Column(name = "price", columnDefinition = "DECIMAL(5, 2)")
    private BigDecimal price;

    @NotNull
    private String description;

    public Item(String name, ItemType type, BigDecimal price, String description) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
    }

    public ItemDTO toDTO() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(name);
        itemDTO.setId(id);
        itemDTO.setPrice(price);
        itemDTO.setDescription(description);
        itemDTO.setTypeId(type.getId());
        itemDTO.setTypeName(type.getName());
        return itemDTO;
    }
}
