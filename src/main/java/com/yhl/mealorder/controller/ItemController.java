package com.yhl.mealorder.controller;

import com.yhl.mealorder.DTO.ItemDTO;
import com.yhl.mealorder.service.ItemService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDTO> getAllItems(@RequestParam(required = false) Long typeId, Pageable pageRequest) {
        // 如果要搜尋特定種類
        if (null != typeId) {
            return itemService.getByTypeId(typeId, pageRequest);
        }
        return itemService.getAllItems(pageRequest);
    }

    @GetMapping("/{id}")
    public ItemDTO getById(@PathVariable Long id) {
        return itemService.getById(id);
    }
}
