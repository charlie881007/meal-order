package com.yhl.mealorder.service;

import com.yhl.mealorder.DTO.ItemTypeWithItemsDTO;
import com.yhl.mealorder.DTO.ItemTypeWithoutItemsDTO;
import com.yhl.mealorder.entity.ItemType;
import com.yhl.mealorder.exception.InvalidArgumentException;
import com.yhl.mealorder.repository.ItemTypeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTypeService {
    private final ItemTypeRepository itemTypeRepository;

    public ItemTypeService(ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    public List<ItemTypeWithoutItemsDTO> getAllItemTypes(Pageable pageRequest) {
        return itemTypeRepository.findAll(pageRequest).stream().map(ItemType::toSimpleDTO).toList();
    }

    public ItemTypeWithItemsDTO getById(Long id) {
        return itemTypeRepository.findById(id).orElseThrow(InvalidArgumentException::new).toDetailedDTO();
    }
}
