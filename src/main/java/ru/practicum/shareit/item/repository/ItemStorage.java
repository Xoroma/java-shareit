package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface ItemStorage {
    ItemDto addItem(ItemDto dto, User owner);

    ItemDto patchItem(ItemDto dto, User owner, long itemId) throws NotFoundException;

    ItemDto getItem(long itemId, long ownerId);

    List<ItemDto> getAllItemsByOwner(long ownerId);

    List<ItemDto> searchItem(String text, long ownerId);
}
