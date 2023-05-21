package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.FullBookingDto;
import ru.practicum.shareit.booking.model.enums.BookingState;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
@Validated
public class BookingController {
    private final BookingService bookingService;

    private final String userHeader = "X-Sharer-User-Id";

    @PostMapping
    public FullBookingDto addBooking(@Valid @RequestBody BookingDto dto,
                                     @RequestHeader(userHeader) long bookerId)
            throws NotFoundException, BadRequestException {
        log.info("POST /bookings");
        return bookingService.addBooking(dto, bookerId);
    }

    @PatchMapping("/{bookingId}")
    public FullBookingDto approvingByOwner(@PathVariable long bookingId, @RequestParam boolean approved,
                                           @RequestHeader(userHeader) long bookerId)
            throws NotFoundException, BadRequestException {
        log.info("PATCH /bookings/" + bookingId + "?approved=" + approved);
        return bookingService.approvingByOwner(bookingId, approved, bookerId);
    }

    @GetMapping("/{bookingId}")
    public FullBookingDto getBooking(@PathVariable long bookingId, @RequestHeader(userHeader) long bookerId)
            throws NotFoundException {
        log.info("GET /bookings/" + bookingId);
        return bookingService.getBooking(bookingId, bookerId);
    }

    @GetMapping
    public List<FullBookingDto> getAllBookingsByBookerId(@RequestHeader(userHeader) long bookerId,
                                                         @RequestParam(defaultValue = "ALL") BookingState state,
                                                         @RequestParam(required = false, defaultValue = "0")
                                                         @Min(0) Integer from,
                                                         @RequestParam(required = false, defaultValue = "10")
                                                         Integer size) {
        log.info("GET /bookings?state=" + state.toString());
        return bookingService.getAllBookingsByBookerId(bookerId, state, from, size);
    }

    @GetMapping("/owner")
    public List<FullBookingDto> getAllBookingItemsByBookerId(@RequestHeader(userHeader) long ownerId,
                                                             @RequestParam(defaultValue = "ALL") BookingState state,
                                                             @RequestParam(required = false, defaultValue = "0")
                                                             @Min(0) Integer from,
                                                             @RequestParam(required = false, defaultValue = "10")
                                                             Integer size) {
        log.info("GET /bookings/owner?state=" + state.toString());
        return bookingService.getAllBookingByItemsByOwnerId(ownerId, state, from, size);
    }
}

