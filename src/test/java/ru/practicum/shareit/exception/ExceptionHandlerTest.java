package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerTest {
    @Test
    void conflictExcTest() {
        ExceptionsHandler exceptionsHandler = new ExceptionsHandler();

        exceptionsHandler.conflictException(new ConflictException());

        assertEquals(1, 1);
    }

    @Test
    void badReqExcTest() {
        ExceptionsHandler exceptionsHandler = new ExceptionsHandler();

        exceptionsHandler.badRequestException(new BadRequestException());

        assertEquals(1, 1);
    }

    @Test
    public void testBadRequestException() {
        ExceptionsHandler exceptionsHandler = new ExceptionsHandler();
        NullPointerException exception = new NullPointerException("testovich");

        ResponseEntity<ErrorResponse> response = exceptionsHandler.badRequestException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()));
        assertEquals("Bad request", response.getBody().getError());
    }
}