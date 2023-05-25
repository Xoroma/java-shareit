package ru.practicum.shareit.booking.service;

import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.FullBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.BookingState;
import ru.practicum.shareit.exception.BadRequestException;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    FullBookingDto addBooking(BookingDto dto, long bookerId) ;

    FullBookingDto approvingByOwner(long bookingId, boolean approved, long bookerId) ;

    FullBookingDto getBooking(long bookingId, long bookerId);

    List<FullBookingDto> getAllBookingsByBookerId(long bookerId, BookingState state, Integer from, Integer size);

    List<FullBookingDto> getAllBookingByItemsByOwnerId(long ownerId, BookingState state, Integer from, Integer size);

    List<Booking> allBookingsForItem(Long itemId, Sort sort);

    List<Booking> findAllByItemsOwnerId(Long ownerId);

    List<Booking> bookingsForItemAndBookerPast(Long bookerId, Long itemId, LocalDateTime now);
}
