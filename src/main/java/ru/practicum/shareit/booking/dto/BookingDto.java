package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
public class BookingDto {
    @NotNull
    private long bookingId;
    @FutureOrPresent(message = "Start of booking must be in a future")
    @NotNull(message = "Start of booking can't be empty")
    private LocalDateTime start;
    @FutureOrPresent(message = "End of booking must be in a future")
    @NotNull(message = "End of booking can't be empty")
    private LocalDateTime end;
    private Item item;
    private User booker;
    private Status status;
}

