package ru.practicum.shareit.exeption;

public class ValidateExeption extends RuntimeException{

    public ValidateExeption(String message) {
        super(message);
    }
}
