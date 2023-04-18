package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto createItem(ItemDto itemDto, long ownerId);
    ItemDto getItemById(Long id);

    List<ItemDto> getAllItemsByOwnerId(Long ownerId);

    ItemDto updateItem(Long itemId, ItemDto itemDto, Long ownerId);

    List<ItemDto> searchAvailableItems(String text);
}
