package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.FullBookingDto;
import ru.practicum.shareit.booking.model.enums.BookingState;

import java.util.List;

public interface BookingService {
    FullBookingDto addBooking(BookingDto dto, long bookerId);

    FullBookingDto approvingByOwner(long bookingId, boolean approved, long bookerId);

    FullBookingDto getBooking(long bookingId, long bookerId);

    List<FullBookingDto> getAllBookingsByBookerId(long bookerId, BookingState state, Integer from, Integer size);

    List<FullBookingDto> getAllBookingByItemsByOwnerId(long ownerId, BookingState state, Integer from, Integer size);
}
