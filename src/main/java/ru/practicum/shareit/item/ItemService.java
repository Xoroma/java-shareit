package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.dto.GetItemDto;
import ru.practicum.shareit.item.model.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto addItem(ItemDto dto, long ownerId);

    ItemDto patchItem(ItemDto dto,long ownerId, long itemId);

    GetItemDto getItem(long itemId, long ownerId);

    List<GetItemDto> getAllItemsByOwner(long ownerId, Integer from, Integer size);

    List<ItemDto> searchItem(String text, long ownerId, Integer from, Integer size);

    Comment addComment(Comment dto, long itemId, long authorId);
}
