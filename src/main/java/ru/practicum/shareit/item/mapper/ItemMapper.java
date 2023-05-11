package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.item.dto.ItemDtoWithCommments;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ItemMapper {

    public static ItemDtoWithCommments mapToItemDtoWithComments(Item item, List<Booking> bookings,
                                                                List<Comment> comments) {

        ItemDtoWithCommments itemDto = new ItemDtoWithCommments(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable()
        );

        if (bookings != null && !bookings.isEmpty()) {
            LocalDateTime now = LocalDateTime.now();

            Optional<Booking> maybeLast = bookings.stream()
                    .filter(booking -> booking.getStart().isBefore(now))
                    .max(Comparator.comparing(Booking::getEnd));

            maybeLast.ifPresent(itemDto::setLastBooking);

            Optional<Booking> maybeNext = bookings.stream()
                    .filter(booking -> booking.getStart().isAfter(now))
                    .filter(booking -> !booking.getStatus().equals(Status.REJECTED))
                    .min(Comparator.comparing(Booking::getStart));

            maybeNext.ifPresent(itemDto::setNextBooking);
        }

        itemDto.setComments(comments);

        return itemDto;
    }

    public static ItemDto mapToItemDto(Item item) {
        return new ItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable()

        );
    }

    public static Item mapToItem(ItemDto dto, long owner) {
        return new Item(dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getAvailable(),
                owner,
                0
        );
    }

}
