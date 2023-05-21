package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDtoComments;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private final String userHeader = "X-Sharer-User-Id";
    private final ItemService itemService;


    @PostMapping
    public ItemDto addItem(@RequestBody @Valid ItemDto dto, @RequestHeader(userHeader) long ownerId) {
        log.info("Request POST /items");
        return itemService.addItem(dto,ownerId);
    }

    @GetMapping(value = "/{itemId}")
    public ItemDtoComments getItem(@PathVariable long itemId, @RequestHeader(userHeader) long ownerId) {
        log.info(String.format("Получен запрос GET /items/%s", itemId));
        return itemService.getItem(itemId,ownerId);
    }

    @PatchMapping(value = "/{itemId}")
    public ItemDto patchItem(@RequestBody ItemDto dto, @PathVariable long itemId,
                             @RequestHeader(userHeader) long ownerId) throws NotFoundException {

        log.info(String.format("PATCH /items/%s", itemId));
        return itemService.patchItem(dto,ownerId,itemId);
    }


    @GetMapping
    public List<ItemDtoComments> getAllItemsByOwner(@RequestHeader(userHeader) long ownerId,
                                                    @RequestParam(required = false, defaultValue = "0")
                                               @Min(0) Integer from,
                                                    @RequestParam(required = false, defaultValue = "10")
                                               Integer size) {
        log.info("GET /items");
        return itemService.getAllItemsByOwner(ownerId, from, size);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestParam String text, @RequestHeader(userHeader) long ownerId,
                                    @RequestParam(required = false, defaultValue = "0")
                                    @Min(0) Integer from,
                                    @RequestParam(required = false, defaultValue = "10")
                                    Integer size) {
        log.info(String.format("Получен запрос GET /items/search?text=%s", text));
        return itemService.searchItem(text.toLowerCase(), ownerId, from, size);
    }

    @PostMapping("{itemId}/comment")
    public Comment addComment(@RequestBody @Valid Comment dto, @PathVariable long itemId,
                              @RequestHeader(userHeader) long authorId) {

        log.info("Request POST /items/" + itemId + "/comment");
        return itemService.addComment(dto, itemId, authorId);
    }
}

