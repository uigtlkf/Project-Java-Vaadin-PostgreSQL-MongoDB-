package com.example.application.data.services.exceptions;

public class InvalidPaymentInfoException extends Exception {
    public InvalidPaymentInfoException(String message) {
        super(message);
    }
}