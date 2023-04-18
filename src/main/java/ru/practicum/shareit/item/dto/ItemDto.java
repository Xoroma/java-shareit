package ru.practicum.shareit.item.dto;


import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private User ownerId;
    private ItemRequest request; /*если вещь была создана по запросу другого пользователя, то в этом поле будет хранится
                           ссылка на соответвующий запрос.*/

}
