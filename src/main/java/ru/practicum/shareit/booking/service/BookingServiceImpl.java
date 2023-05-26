package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.dto.FullBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.BookingState;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public FullBookingDto addBooking(BookingDto dto, long bookerId) {
        if ((itemRepository.findById(dto.getItemId()).isEmpty() || userRepository.findById(bookerId).isEmpty())
                || itemRepository.findById(dto.getItemId()).get().getOwnerId() == bookerId) {
            throw new NotFoundException();
        }
        if (itemRepository.findById(dto.getItemId()).get().isAvailable()
                && dto.getEnd().isAfter(dto.getStart())) {
            return BookingMapper.toFullBookingFromBooking(
                    bookingRepository.save(BookingMapper.toBooking(dto, bookerId, Status.WAITING)),
                    Status.WAITING, itemRepository, userRepository);
        } else {
            throw new BadRequestException();
        }
    }

    @Override
    public FullBookingDto approvingByOwner(long bookingId, boolean approved, long itemOwnerId) {
        if (itemRepository.findById(bookingRepository.findById(bookingId).get()
                .getItemId()).get().getOwnerId() != itemOwnerId) {
            throw new NotFoundException();
        }
        if (bookingRepository.findById(bookingId).isPresent()) {
            long bookerId = bookingRepository.findById(bookingId).get().getBookerId();
            BookingDto dto = BookingMapper.toBookingDto(bookingRepository.findById(bookingId).get());
            dto.setId(bookingId);
            Booking booking;
            Status status;
            if (bookingRepository.findById(bookingId).get().getStatus() == Status.APPROVED && approved) {
                throw new BadRequestException();
            }
            if (approved) {
                status = Status.APPROVED;
            } else {
                status = Status.REJECTED;
            }
            booking = BookingMapper.toBooking(dto, bookerId, status);

            return BookingMapper.toFullBookingFromBooking(bookingRepository.save(booking), status,
                    itemRepository, userRepository);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public FullBookingDto getBooking(long bookingId, long bookerId) {
        Booking booking;
        if (bookingRepository.findById(bookingId).isPresent()) {
            booking = bookingRepository.findById(bookingId).get();
        } else {
            throw new NotFoundException();
        }
        if (booking.getBookerId() != bookerId &&
                itemRepository.findById(booking.getItemId()).get().getOwnerId() != bookerId) {
            throw new NotFoundException();
        }
        Status status = booking.getStatus();
        return BookingMapper.toFullBookingFromBooking(booking, status, itemRepository, userRepository);
    }

    @Override
    public List<FullBookingDto> getAllBookingsByBookerId(long bookerId, BookingState state, Integer from, Integer size) {
        if (userRepository.findById(bookerId).isEmpty()) {
            throw new NotFoundException();
        }
        PageRequest pageRequest = PageRequest.of((from / size), size, Sort.by(Sort.Direction.DESC, "start"));
        switch (state) {
            case ALL:
                return bookingRepository.findAllByBookerId(bookerId,
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .collect(Collectors.toList());
            case PAST:
                return bookingRepository.findByBookerIdAndEndAfter(bookerId, LocalDateTime.now(),
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingRepository.findByBookerIdAndStartAfter(bookerId, LocalDateTime.now(),
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .collect(Collectors.toList());
            case CURRENT:
                return bookingRepository.findByBookerIdAndEndIsBeforeAndStartIsAfter(bookerId, LocalDateTime.now(),
                                LocalDateTime.now(),
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .collect(Collectors.toList());
            case WAITING:
                return bookingRepository.findAllByBookerIdAndStatus(bookerId, Status.WAITING,
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .collect(Collectors.toList());
            case REJECTED:
                return bookingRepository.findAllByBookerIdAndStatus(bookerId, Status.REJECTED,
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .collect(Collectors.toList());
            default:
                return null;

        }

    }

    @Override
    public List<FullBookingDto> getAllBookingByItemsByOwnerId(long ownerId, BookingState state, Integer from, Integer size) {
        if (userRepository.findById(ownerId).isEmpty()) {
            throw new NotFoundException();
        }
        PageRequest pageRequest = PageRequest.of((from / size), size, Sort.by(Sort.Direction.DESC, "start"));
        switch (state) {

            case ALL:
                return bookingRepository.bookingsForItem(ownerId,
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .collect(Collectors.toList());
            case PAST:
                return bookingRepository.bookingsForItemPast(ownerId, LocalDateTime.now(),
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingRepository.bookingsForItemFuture(ownerId, LocalDateTime.now(),
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .collect(Collectors.toList());
            case CURRENT:
                return bookingRepository.bookingsForItemCurrent(ownerId, LocalDateTime.now(),
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .collect(Collectors.toList());
            case WAITING:
                return bookingRepository.bookingsForItem(ownerId,
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .filter(l -> l.getStatus() == Status.WAITING)
                        .collect(Collectors.toList());
            case REJECTED:
                return bookingRepository.bookingsForItem(ownerId,
                                pageRequest)
                        .stream()
                        .map(l -> BookingMapper.toFullBookingFromBooking(l, l.getStatus(),
                                itemRepository, userRepository))
                        .filter(l -> l.getStatus() == Status.REJECTED)
                        .collect(Collectors.toList());

        }
        return null;
    }
}
