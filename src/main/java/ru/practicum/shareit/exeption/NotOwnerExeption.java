package ru.practicum.shareit.exeption;

public class NotOwnerExeption extends RuntimeException {

    public NotOwnerExeption(String message) {
        super(message);
    }
}
