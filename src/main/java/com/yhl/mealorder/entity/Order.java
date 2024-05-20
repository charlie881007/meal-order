package com.yhl.mealorder.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @ManyToMany
    @JoinTable(name = "order_item", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;

    @NotNull
    private LocalDateTime createTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    public Order(String phoneNum, List<Item> items, LocalDateTime createTime, Status status) {
        this.phoneNum = phoneNum;
        this.items = items;
        this.createTime = createTime;
        this.status = status;
    }

    public enum Status {
        IN_PROGRESS, COMPLETED
    }
}