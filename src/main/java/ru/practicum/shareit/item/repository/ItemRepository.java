package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface ItemRepository {
    Item create(Item item, User owner);

    Item update(Long itemId, Item item, User owner);

    Item getItemById(long id);

    List<Item> getAllItemsByOwnerId(User owner);

    List<Item> searchAvailableItems(String text);
}
