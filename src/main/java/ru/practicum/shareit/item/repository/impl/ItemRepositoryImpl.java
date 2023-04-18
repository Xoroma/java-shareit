package ru.practicum.shareit.item.repository.impl;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    Long idCounter = 0L;
    Map<Long, Item> items = new HashMap<>();

    @Override
    public Item create(Item item, User owner) {
        idCounter++;
        item.setId(idCounter);
        item.setOwner(owner);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item getItemById(long id) {
        return items.get(id);
    }

    @Override
    public List<Item> getAllItemsByOwnerId(User owner) {
        return items.values().stream().filter(i -> i.getOwner().equals(owner)).collect(Collectors.toList());

    }

    @Override
    public Item update(Long itemId, Item item, User owner) {
        items.put(itemId, item);
        return items.get(itemId);
    }

    @Override
    public List<Item> searchAvailableItems(String text) {
        return items.values().stream()
                .filter(item -> item.toString().toLowerCase().contains(text.toLowerCase()) && item.getAvailable())
                .collect(Collectors.toList());
    }

}
