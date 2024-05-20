package com.yhl.mealorder.repository;

import com.yhl.mealorder.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.type.id = :typeId")
    List<Item> findByTypeId(@Param("typeId") Long typeId, Pageable pageRequest);
}
