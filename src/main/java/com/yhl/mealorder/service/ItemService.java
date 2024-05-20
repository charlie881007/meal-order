package com.yhl.mealorder.service;

import com.yhl.mealorder.DTO.ItemsDTO;
import com.yhl.mealorder.entity.Item;
import com.yhl.mealorder.repository.ItemRepository;
import org.springframework.stereotype.Service;


@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemsDTO getAllItems() {
        ItemsDTO itemsDTO = new ItemsDTO();
        itemsDTO.setItems(itemRepository.findAll().stream().map(Item::toDTO).toList());
        return itemsDTO;
    }
}
