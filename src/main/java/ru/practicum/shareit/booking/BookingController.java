package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.FullBookingDto;
import ru.practicum.shareit.booking.model.enums.BookingState;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    private final String userHeader = "X-Sharer-User-Id";

    @PostMapping
    public FullBookingDto createBooking(@Valid @RequestBody BookingDto dto, @RequestHeader(userHeader) long bookerId)
            throws NotFoundException, BadRequestException {

        log.info("Request from POST /bookings");
        return bookingService.addBooking(dto, bookerId);
    }

    @PatchMapping("/{bookingId}")
    public FullBookingDto approveBooking(@PathVariable long bookingId, @RequestParam boolean approved, @RequestHeader(userHeader) long bookerId)
            throws NotFoundException, BadRequestException {

        log.info("Request from PATCH /bookings/ " + bookingId + "?approved= " + approved);
        return bookingService.approveBooking(bookingId, approved, bookerId);
    }

    @GetMapping("/{bookingId}")
    public FullBookingDto getBooking(@PathVariable long bookingId, @RequestHeader(userHeader) long bookerId)
            throws NotFoundException {

        log.info("Request GET /bookings/ " + bookingId);
        return bookingService.getBooking(bookingId, bookerId);
    }

    @GetMapping
    public List<FullBookingDto> getAllBookingsByBookerId(@RequestHeader(userHeader) long bookerId,
                                                         @RequestParam(defaultValue = "ALL") BookingState state) {

        log.info("Request GET /bookings?state= " + state.toString());
        return bookingService.getAllBookingsByBookerId(bookerId, state);
    }

    @GetMapping("/owner")
    public List<FullBookingDto> getAllBookingItemsByBookerId(@RequestHeader(userHeader) long ownerId,
                                                             @RequestParam(defaultValue = "ALL") BookingState state) {

        log.info("Request GET /bookings/owner?state=" + state.toString());
        return bookingService.getAllBookingByItemsByOwnerId(ownerId, state);
    }

}

