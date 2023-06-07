package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.ShortBookingDto;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {

    long id;

    String name;

    String description;

    @NotNull
    Boolean available;

    ShortBookingDto lastBooking;

    ShortBookingDto nextBooking;

    Collection<CommentDto> comments;

    Long requestId;

}