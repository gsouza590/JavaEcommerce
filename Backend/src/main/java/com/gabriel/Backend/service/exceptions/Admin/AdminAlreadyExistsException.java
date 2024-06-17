package com.gabriel.Backend.service.exceptions.Admin;

public class AdminAlreadyExistsException extends RuntimeException {
    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}
