package ru.practicum.shareit.item;

import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.dto.GetItemDto;
import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto addItem(ItemDto dto, long ownerId) throws NotFoundException;

    ItemDto patchItem(ItemDto dto,long ownerId, long itemId) throws NotFoundException;

    GetItemDto getItem(long itemId, long ownerId);

    List<GetItemDto> getAllItemsByOwner(long ownerId, Integer from, Integer size);

    List<ItemDto> searchItem(String text, long ownerId, Integer from, Integer size);

    Comment addComment(Comment dto, long itemId, long authorId) throws BadRequestException;
}
