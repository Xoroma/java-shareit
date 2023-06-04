package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = " select b" +
            " from Booking b " +
            " join User u on (u.id = b.bookerId)" +
            " where b.bookerId = ?1 and b.end > ?2 and b.start < ?2"
    )
    List<Booking> findByBookerIdAndEndIsBeforeAndStartIsAfter(Long bookerId, LocalDateTime end,
                                                              LocalDateTime start, Pageable pageable);

    List<Booking> findByBookerIdAndStartAfter(Long bookerId, LocalDateTime start, Pageable pageable);

    @Query(value = " select b" +
            " from Booking b " +
            " join User u on (u.id = b.bookerId)" +
            " where u.id = ?1 and b.end < ?2"
    )
    List<Booking> findByBookerIdAndEndAfter(Long bookerId, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByBookerId(Long bookerId, Pageable pageable);

    List<Booking> findAllByBookerIdAndStatus(Long bookerId, Status status, Pageable pageable);

    @Query(value = " select b" +
            " from Booking b " +
            " join Item i on (i.id = b.itemId) " +
            " join User u on (u.id = i.ownerId)" +
            " where i.ownerId = ?1"
    )
    List<Booking> bookingsForItem(Long ownerId, Pageable pageable);

    @Query(value = " select b" +
            " from Booking b " +
            " join Item i on (i.id = b.itemId) " +
            " where i.id = ?1"
    )
    List<Booking> allBookingsForItem(Long itemId, Sort sort);

    @Query(value = " select b" +
            " from Booking b " +
            " join Item i on (i.id = b.itemId) " +
            " join User u on (u.id = i.ownerId)" +
            " where i.ownerId = ?1 and b.end < ?2"
    )
    List<Booking> bookingsForItemPast(Long ownerId, LocalDateTime now, Pageable pageable);

    @Query(value = " select b" +
            " from Booking b " +
            " join Item i on (i.id = b.itemId) " +
            " join User u on (u.id = i.ownerId)" +
            " where b.bookerId = ?1 and i.id = ?2 and b.end < ?3"
    )
    List<Booking> bookingsForItemAndBookerPast(Long bookerId, Long itemId, LocalDateTime now);

    @Query(value = " select b" +
            " from Booking b " +
            " join Item i on (i.id = b.itemId) " +
            " join User u on (u.id = i.ownerId)" +
            " where i.ownerId = ?1 and b.start > ?2"
    )
    List<Booking> bookingsForItemFuture(Long ownerId, LocalDateTime now, Pageable pageable);

    @Query(value = " select b" +
            " from Booking b " +
            " join Item i on (i.id = b.itemId) " +
            " join User u on (u.id = i.ownerId)" +
            " where i.ownerId = ?1 and b.end >= ?2 and b.start < ?2"
    )
    List<Booking> bookingsForItemCurrent(Long ownerId, LocalDateTime now, Pageable pageable);

    @Query(value = "select b from Booking b " +
            "join Item i on i.id = b.itemId " +
            "join User u on u.id = i.ownerId " +
            "where i.ownerId = ?1")
    List<Booking> findAllByItemsOwnerId(Long ownerId);
}
