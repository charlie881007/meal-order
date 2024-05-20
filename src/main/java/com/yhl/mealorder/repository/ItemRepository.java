package com.yhl.mealorder.repository;

import com.yhl.mealorder.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
