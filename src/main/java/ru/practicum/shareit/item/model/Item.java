package ru.practicum.shareit.item.model;

import lombok.*;
import ru.practicum.shareit.user.User;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Item {
   private Long id;
   private String name;
   private String description;
   private Boolean available;
   private User owner;
   private Long request; /*если вещь была создана по запросу другого пользователя, то в этом поле будет хранится
                           ссылка на соответвующий запрос.*/
}