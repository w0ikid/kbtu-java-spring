package com.library.book_service.domain.exception;

import java.util.UUID;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(UUID id) {
        super("Book not available: " + id);
    }
}