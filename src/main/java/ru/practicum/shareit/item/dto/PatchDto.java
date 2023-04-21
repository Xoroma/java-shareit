package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.user.User;

@Data
public class PatchDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private User ownerId;
    private Long request; /*если вещь была создана по запросу другого пользователя, то в этом поле будет хранится
                           ссылка на соответвующий запрос.*/
}
