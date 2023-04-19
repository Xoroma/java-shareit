package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.PatchDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@AllArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") long ownerId, @RequestBody @Valid ItemDto itemDto) {
        log.debug("ItemController POST /items with body {}", itemDto);
        return itemService.createItem(itemDto, ownerId);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable long id) {
        log.debug("ItemController GET /items/{}", id);
        return itemService.getItemById(id);
    }

    @GetMapping
    public List<ItemDto> getAllItemsByOwner(@RequestHeader("X-Sharer-User-Id") long ownerId) {
        log.debug("ItemController GET /items/");
        return itemService.getAllItemsByOwnerId(ownerId);
    }


    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") long ownerId,
                          @RequestBody @Valid PatchDto itemDto) {
        log.debug("ItemController PATCH /items with body {}", itemDto);
        return itemService.updateItem(itemId, itemDto, ownerId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchAvailableItem(@RequestParam(name = "text") String text) {
        log.debug("ItemController GET /searchAvailableItem/{}", text);
        return itemService.searchAvailableItems(text);
    }

}
