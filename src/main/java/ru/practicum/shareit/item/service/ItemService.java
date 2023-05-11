package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDtoWithCommments;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

public interface ItemService {

    ItemDto addItem(ItemDto dto, long ownerId);

    List<ItemDtoWithCommments> getAllItemsByOwner(long ownerId);

    ItemDtoWithCommments getItem(long itemId, long ownerId);


    ItemDto updateItem(ItemDto dto, long ownerId, long itemId);

    List<ItemDto> searchItem(String text, long ownerId);

    Comment addComment(Comment dto, long itemId, long authorId);

}
