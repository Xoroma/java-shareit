package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.FullBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.BookingState;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    FullBookingDto addBooking(BookingDto dto, long bookerId) ;

    FullBookingDto approveBooking(long bookingId, boolean approved, long bookerId);

    FullBookingDto getBooking(long bookingId, long bookerId);

    List<FullBookingDto> getAllBookingsByBookerId(long bookerId, BookingState state);

    List<FullBookingDto> getAllBookingByItemsByOwnerId(long ownerId, BookingState state);

    List<Booking> allBookingsForItem(Long itemId);
    List<Booking> findAllByItemsOwnerId(Long ownerId);
    List<Booking> bookingsForItemAndBookerPast(Long bookerId, Long itemId, LocalDateTime now);
}
