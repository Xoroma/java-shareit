package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDtoWithCommments {
    long id;

    @NotNull
    @NotBlank
    String name;

    @NotNull
    String description;
    @NotNull
    Boolean available;

    Booking lastBooking;

    Booking nextBooking;

    List<Comment> comments;

    public ItemDtoWithCommments(long id, String name, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }
}

