package com.library.book_service.app;

import com.library.book_service.domain.Book;
import com.library.book_service.domain.BookStatus;
import com.library.book_service.domain.exception.BookNotAvailableException;
import com.library.book_service.domain.exception.BookNotFoundException;
import com.library.book_service.infra.BookBorrowedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

import com.library.book_service.infra.BookRepository;
import com.library.book_service.infra.BorrowRecordRepository;
import com.library.book_service.domain.BorrowRecord;
import com.library.book_service.infra.BookEventProducer;

import com.library.book_service.api.dto.BookRequest;
import com.library.book_service.api.dto.BookResponse;
import com.library.book_service.api.dto.BorrowRequest;
import com.library.book_service.api.dto.BorrowResponse;
import com.library.book_service.api.dto.CancelBorrowRequest;
import com.library.book_service.api.mapper.BookMapper;
import com.library.book_service.infra.CancelBorrowEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final BookEventProducer bookEventProducer;
    private final BookMapper bookMapper;

    public BookResponse createBook(BookRequest request) {
        log.info("Creating book: {}", request.title());
        Book book = bookMapper.toEntity(request);
        book.setStatus(BookStatus.AVAILABLE);
        book.setCreatedAt(LocalDateTime.now());
        Book saved = bookRepository.save(book);
        log.debug("Book saved with id: {}", saved.getId());
        return bookMapper.toResponse(saved);
    }

    public BookResponse getById(UUID id) {
        log.info("Fetching book by id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.toResponse(book);
    }

    public Page<BookResponse> getAll(Pageable pageable) {
        log.info("Fetching all books, page: {}", pageable.getPageNumber());
        return bookRepository.findAll(pageable)
                .map(bookMapper::toResponse);
    }

    public BookResponse updateBook(UUID id, BookRequest request) {
        log.info("Updating book: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setAuthorEmail(request.authorEmail());
        book.setPrice(request.price());
        book.setDescription(request.description());
        return bookMapper.toResponse(bookRepository.save(book));
    }

    public void deleteBook(UUID id) {
        log.info("Deleting book: {}", id);
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    public BorrowResponse borrowBook(UUID bookId, BorrowRequest request) {
        log.info("Borrowing book: {} by member: {}", bookId, request.memberId());

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (book.getStatus() == BookStatus.BORROWED) {
            throw new BookNotAvailableException(bookId);
        }

        book.setStatus(BookStatus.BORROWED);
        bookRepository.save(book);

        BorrowRecord record = BorrowRecord.builder()
                .bookId(bookId)
                .memberId(request.memberId())
                .borrowedAt(LocalDateTime.now())
                .build();
        borrowRecordRepository.save(record);

        bookEventProducer.publishBookBorrowed(new BookBorrowedEvent(
                bookId,
                book.getTitle(),
                request.memberId(),
                record.getBorrowedAt()
        ));

        log.info("Book {} borrowed successfully by member {}", bookId, request.memberId());
        return new BorrowResponse(record.getId(), bookId, request.memberId(), record.getBorrowedAt());
    }

    @Transactional
    public void cancelBorrow(UUID bookId, CancelBorrowRequest request) {
        log.info("Cancelling borrow for book: {} by member: {}", bookId, request.memberId());

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        BorrowRecord record = borrowRecordRepository.findByBookIdAndMemberIdAndReturnedAtIsNull(bookId, request.memberId())
                .orElseThrow(() -> new RuntimeException("No active borrow record found for book " + bookId + " and member " + request.memberId()));

        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        record.setReturnedAt(LocalDateTime.now());
        borrowRecordRepository.save(record);

        bookEventProducer.publishCancelBorrow(new CancelBorrowEvent(
                bookId,
                book.getTitle(),
                request.memberId(),
                record.getReturnedAt()
        ));

        log.info("Borrow for book {} cancelled successfully by member {}", bookId, request.memberId());
    }
}
