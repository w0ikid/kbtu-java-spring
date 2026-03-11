package com.library.notification_service.infra;

import com.library.notification_service.app.NotificationService;
import com.library.notification_service.infra.event.BookBorrowedEvent;
import com.library.notification_service.infra.event.CancelBorrowEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "book-borrowed", groupId = "notification-service-group")
    public void handleBookBorrowed(BookBorrowedEvent event) {
        log.info("Received BookBorrowedEvent for book: {} by member: {}", event.bookId(), event.memberId());
        try {
            notificationService.notifyBorrow(event.memberId(), event.bookId(), event.bookTitle(), event.borrowedAt());
        } catch (Exception e) {
            log.error("Error processing BookBorrowedEvent: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "cancel-borrow", groupId = "notification-service-group")
    public void handleCancelBorrow(CancelBorrowEvent event) {
        log.info("Received CancelBorrowEvent for book ID: {} by member: {}", event.bookId(), event.memberId());
        try {
            notificationService.notifyCancel(event.memberId(), event.bookId(), event.bookTitle(), event.cancelledAt());
            log.info("Successfully processed CancelBorrowEvent for book: {}", event.bookId());
        } catch (Exception e) {
            log.error("Error processing CancelBorrowEvent: {}", e.getMessage());
        }
    }
}
