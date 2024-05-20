package com.yhl.mealorder.controller;

import com.yhl.mealorder.DTO.ItemsDTO;
import com.yhl.mealorder.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;

    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }
    @GetMapping
    public ItemsDTO getAllItems(){
        return itemService.getAllItems();
    }
}
