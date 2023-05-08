package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.FullBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

public class BookingMapper {

    public static BookingDto mapToBookingDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .itemId(booking.getItemId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();
    }

    public static Booking mapToBooking(BookingDto dto, long bookerId, Status status) {
        return Booking.builder()
                .id(dto.getId())
                .start(dto.getStart())
                .end(dto.getEnd())
                .itemId(dto.getItemId())
                .bookerId(bookerId)
                .status(status)
                .build();
    }


    public static FullBookingDto toFullBookingFromBooking(Booking booking, Status status,
                                                          ItemRepository itemRepository,
                                                          UserRepository userRepository) {

        User booker = userRepository.findById(booking.getBookerId()).get();

        Item item = itemRepository.findById(booking.getItemId()).get();
        return FullBookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(item)
                .booker(booker)
                .status(status)
                .build();
    }

}

