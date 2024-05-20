package com.yhl.mealorder.controller;

import com.yhl.mealorder.DTO.ItemTypeWithItemsDTO;
import com.yhl.mealorder.DTO.ItemTypeWithoutItemsDTO;
import com.yhl.mealorder.service.ItemTypeService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itemTypes")
public class ItemTypeController {
    private final ItemTypeService itemTypeService;

    public ItemTypeController(ItemTypeService itemTypeService) {
        this.itemTypeService = itemTypeService;
    }

    @GetMapping
    public List<ItemTypeWithoutItemsDTO> getAllItemTypes(Pageable pageRequest) {
        return itemTypeService.getAllItemTypes(pageRequest);
    }

    @GetMapping("/{id}")
    public ItemTypeWithItemsDTO getAllItemTypes(@PathVariable("id") Long id) {
        return itemTypeService.getById(id);
    }
}
