package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.FullBookingDto;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exception.ExceptionsHandler;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BookingController.class, ExceptionsHandler.class})
public class BookingControllerTest {

    @MockBean
    BookingService bookingService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Test
    void createTest() throws Exception {

        final BookingDto bookingDto = BookingDto.builder()
                .itemId(1)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusHours(1)).build();

        ItemDto itemDto = new ItemDto(1, "testovich", "testDescr", true, 0);

        when(bookingService.addBooking(any(), anyLong()))
                .thenReturn(FullBookingDto.builder()
                        .id(1)
                        .start(LocalDateTime.now().minusHours(1))
                        .end(LocalDateTime.now().plusHours(1))
                        .booker(new User(1, "testovich", "testovich@test.com"))
                        .item(ItemMapper.mapToItem(itemDto, 2))
                        .status(Status.WAITING)
                        .build());

        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", "1")
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void approvingByOwner() throws Exception {

        final BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1);
        bookingDto.setItemId(1);
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusHours(1));

        ItemDto itemDto = new ItemDto(1, "testovich", "testDescr", true, 0);

        when(bookingService.approvingByOwner(anyLong(), anyBoolean(), anyLong()))
                .thenReturn(FullBookingDto.builder()
                        .id(1)
                        .start(LocalDateTime.now())
                        .end(LocalDateTime.now().plusHours(1))
                        .booker(new User(1, "testovich", "testovich@test.com"))
                        .item(ItemMapper.mapToItem(itemDto, 2))
                        .status(Status.APPROVED)
                        .build());

        mvc.perform(patch("/bookings/1")
                        .header("X-Sharer-User-Id", "1")
                        .param("approved", "true")
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBooking() throws Exception {

        final BookingDto bookingDto = new BookingDto();

        bookingDto.setId(1);
        bookingDto.setItemId(1);
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusHours(1));
        ItemDto itemDto = new ItemDto(1, "testovich", "testDescr", true, 0);

        when(bookingService.getBooking(anyLong(), anyLong()))
                .thenReturn(FullBookingDto.builder()
                        .id(1)
                        .start(LocalDateTime.now())
                        .end(LocalDateTime.now().plusHours(1))
                        .booker(new User(1, "testovich", "testovich@test.com"))
                        .item(ItemMapper.mapToItem(itemDto, 2))
                        .status(Status.WAITING)
                        .build());

        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", "1")
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBooking() throws Exception {

        final BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1);
        bookingDto.setItemId(1);
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusHours(1));
        ItemDto itemDto = new ItemDto(1, "testovich", "testDescr", true, 0);

        when(bookingService.getAllBookingsByBookerId(anyLong(), any(), anyInt(), anyInt()))
                .thenReturn(List.of(FullBookingDto.builder()
                        .id(1)
                        .start(LocalDateTime.now())
                        .end(LocalDateTime.now().plusHours(1))
                        .booker(new User(1, "testovich", "testovich@test.com"))
                        .item(ItemMapper.mapToItem(itemDto, 2))
                        .status(Status.WAITING)
                        .build()));

        mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", "1")
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBookingItems() throws Exception {

        final BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1);
        bookingDto.setItemId(1);
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusHours(1));
        ItemDto itemDto = new ItemDto(1, "testovich", "testDesrc", true, 0);

        when(bookingService.getAllBookingByItemsByOwnerId(anyLong(), any(), anyInt(), anyInt()))
                .thenReturn(List.of(FullBookingDto.builder()
                        .id(1)
                        .start(LocalDateTime.now())
                        .end(LocalDateTime.now().plusHours(1))
                        .booker(new User(1, "testovich", "testovich@test.com"))
                        .item(ItemMapper.mapToItem(itemDto, 2))
                        .status(Status.WAITING)
                        .build()));

        mvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", "1")
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBookingItemsUnsupportedStatus() throws Exception {

        final BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1);
        bookingDto.setItemId(1);
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusHours(2));

        when(bookingService.getAllBookingByItemsByOwnerId(anyLong(), any(), anyInt(), anyInt()))
                .thenThrow(MethodArgumentTypeMismatchException.class);

        mvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", "1")
                        .content(objectMapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}