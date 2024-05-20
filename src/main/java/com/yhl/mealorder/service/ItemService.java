package com.yhl.mealorder.service;

import com.yhl.mealorder.DTO.ItemDTO;
import com.yhl.mealorder.entity.Item;
import com.yhl.mealorder.repository.ItemRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDTO> getAllItems(Pageable pageRequest) {
        return itemRepository.findAll(pageRequest).stream().map(Item::toDTO).toList();
    }

    public List<ItemDTO> getByTypeId(Long typeId, Pageable pageRequest) {
        return itemRepository.findByTypeId(typeId, pageRequest).stream().map(Item::toDTO).toList();
    }
}
