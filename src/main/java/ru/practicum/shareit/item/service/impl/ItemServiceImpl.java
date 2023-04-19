package ru.practicum.shareit.item.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.NotOwnerExeption;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.PatchDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.mapper.ItemMapper.*;
import static ru.practicum.shareit.user.mapper.UserMapper.mapToUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;


    @Override
    public ItemDto createItem(ItemDto itemDto, long ownerId) {
        User usr = mapToUser(userService.getUserById(ownerId));
        Item item = mapToItem(itemDto, usr);
        return mapToItemDto(itemRepository.create(item, usr));
    }

    @Override
    public ItemDto getItemById(Long id) {
        if (itemRepository.getItemById(id) == null) {
            throw new NotFoundException("Item with " + id + " Id is not found");
        }
        return mapToItemDto(itemRepository.getItemById(id));
    }

    @Override
    public List<ItemDto> getAllItemsByOwnerId(Long ownerId) {
        User usr = mapToUser(userService.getUserById(ownerId));
        return itemRepository.getAllItemsByOwnerId(usr).stream()
                .map(ItemMapper::mapToItemDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchAvailableItems(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.searchAvailableItems(text).stream()
                .map(ItemMapper::mapToItemDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto updateItem(Long itemId, PatchDto itemDto, Long ownerId) {
        Item oldItem = itemRepository.getItemById(itemId);
        User owner = mapToUser(userService.getUserById(ownerId));
        Item item = mapToItemPatchCase(itemDto, owner);
        if (item.getOwner() == null) {
            item.setOwner(owner);
        }
        if (!oldItem.getOwner().equals(owner)) {
            throw new NotOwnerExeption("Owner is incorrect");
        }
        if (item.getName() == null) {
            item.setName(oldItem.getName());
        }
        if (item.getDescription() == null) {
            item.setDescription(oldItem.getDescription());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(oldItem.getAvailable());
        }
        item.setId(itemId);
        return mapToItemDto(itemRepository.update(itemId, item, owner));
    }
    
}
