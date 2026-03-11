package com.library.book_service.api;

import com.library.book_service.api.dto.BookRequest;
import com.library.book_service.api.dto.BookResponse;
import com.library.book_service.api.dto.BorrowRequest;
import com.library.book_service.api.dto.BorrowResponse;
import com.library.book_service.api.dto.CancelBorrowRequest;
import com.library.book_service.domain.BookStatus;
import com.library.book_service.domain.exception.BookNotFoundException;
import com.library.book_service.infra.BookBorrowedEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;
import com.library.book_service.app.BookService;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Book API", description = "Library book management")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Create a new book")
    @ApiResponse(responseCode = "201", description = "Book created")
    @ApiResponse(responseCode = "400", description = "Validation error")
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    @Operation(summary = "Get book by ID")
    @ApiResponse(responseCode = "200", description = "Book found")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @Operation(summary = "Get all books")
    @ApiResponse(responseCode = "200", description = "Books list")
    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(bookService.getAll(pageable));
    }

    @Operation(summary = "Update book")
    @ApiResponse(responseCode = "200", description = "Book updated")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable UUID id,
                                               @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @Operation(summary = "Delete book")
    @ApiResponse(responseCode = "204", description = "Book deleted")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Borrow a book")
    @ApiResponse(responseCode = "200", description = "Book borrowed successfully")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @ApiResponse(responseCode = "409", description = "Book not available")
    @PostMapping("/{id}/borrow")
    public ResponseEntity<BorrowResponse> borrowBook(@PathVariable UUID id, @Valid @RequestBody BorrowRequest request) {
        return ResponseEntity.ok(bookService.borrowBook(id, request));
    }

    @Operation(summary = "Cancel a book borrow")
    @ApiResponse(responseCode = "200", description = "Book borrow cancelled successfully")
    @ApiResponse(responseCode = "404", description = "Book or borrow record not found")
    @PostMapping("/{id}/cancel-borrow")
    public ResponseEntity<Void> cancelBorrow(@PathVariable UUID id, @Valid @RequestBody CancelBorrowRequest request) {
        bookService.cancelBorrow(id, request);
        return ResponseEntity.ok().build();
    }
}