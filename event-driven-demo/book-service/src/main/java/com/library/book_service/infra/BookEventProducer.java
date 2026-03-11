package com.library.book_service.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String BORROW_TOPIC = "book-borrowed";
    private static final String CANCEL_TOPIC = "cancel-borrow";

    public void publishBookBorrowed(BookBorrowedEvent event) {
        kafkaTemplate.send(BORROW_TOPIC, event.bookId().toString(), event);
        log.info("Published BookBorrowedEvent for book: {}", event.bookId());
    }

    public void publishCancelBorrow(CancelBorrowEvent event) {
        kafkaTemplate.send(CANCEL_TOPIC, event.bookId().toString(), event);
        log.info("Published CancelBorrowEvent for book: {}", event.bookId());
    }
}