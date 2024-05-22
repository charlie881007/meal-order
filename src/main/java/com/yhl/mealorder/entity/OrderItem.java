package com.yhl.mealorder.entity;

import com.yhl.mealorder.DTO.OrderDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @NotNull
    private Integer quantity;

    @NotNull
    @Column(name = "amount", columnDefinition = "DECIMAL(5, 2)")
    private BigDecimal amount;

    public OrderItem(Order order, Item item, @NotNull Integer quantity) {
        this.order = order;
        this.item = item;
        this.quantity = quantity;

        this.amount = calcAmount(this);
    }

    public OrderDTO.OrderItemDTO toDTO() {
        OrderDTO.OrderItemDTO orderItemDTO = new OrderDTO.OrderItemDTO();

        orderItemDTO.setItemId(item.getId());
        orderItemDTO.setName(item.getName());
        orderItemDTO.setQuantity(quantity);
        orderItemDTO.setAmount(amount);

        return orderItemDTO;
    }

    public static BigDecimal calcAmount(OrderItem orderItem) {
        BigDecimal amount = BigDecimal.ZERO;
        amount = amount.add(orderItem.getItem().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

        return amount;
    }
}