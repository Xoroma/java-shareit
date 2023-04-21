package ru.practicum.shareit.request;

import lombok.Data;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */@Data
public class ItemRequest {
     @NotNull
    private long id;
    @NotBlank(message = "ItemRequest description can't be empty")
    private String description;
    private User requestor;
    private LocalDateTime created;
}
