package com.gabriel.Backend.service.exceptions.Admin;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}