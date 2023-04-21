package ru.practicum.shareit.exeption;

public class AlreadyExistExeption extends RuntimeException {

    public AlreadyExistExeption(String message) {
        super(message);
    }
}
