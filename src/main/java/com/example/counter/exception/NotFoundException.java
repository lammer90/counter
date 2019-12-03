package com.example.counter.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("Объект не найден.");
    }
}
