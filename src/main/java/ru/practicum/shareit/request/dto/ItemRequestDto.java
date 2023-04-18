package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
public class ItemRequestDto {

    private long id;
    private String description;
    private User requestor;
    private LocalDateTime created;
}
