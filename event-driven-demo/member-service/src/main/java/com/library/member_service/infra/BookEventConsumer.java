package com.library.member_service.infra;

import com.library.member_service.app.MemberService;
import com.library.member_service.infra.event.BookBorrowedEvent;
import com.library.member_service.infra.event.CancelBorrowEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookEventConsumer {

    private final MemberService memberService;

    @KafkaListener(topics = "book-borrowed", groupId = "member-service-group")
    public void handleBookBorrowed(BookBorrowedEvent event) {
        log.info("Received BookBorrowedEvent for book: {} by member: {}", event.bookId(), event.memberId());
        try {
            memberService.incrementBooksCount(event.memberId());
            memberService.addBorrowHistory(event.memberId(), event.bookId(), event.bookTitle(), event.borrowedAt());
        } catch (Exception e) {
            log.error("Error processing BookBorrowedEvent: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "cancel-borrow", groupId = "member-service-group")
    public void handleCancelBorrow(CancelBorrowEvent event) {
        log.info("Received CancelBorrowEvent for book: {} by member: {}", event.bookId(), event.memberId());
        try {
            memberService.decrementBooksCount(event.memberId());
            log.info("Successfully processed CancelBorrowEvent for book: {}", event.bookId());
        } catch (Exception e) {
            log.error("Error processing CancelBorrowEvent: {}", e.getMessage());
        }
    }
}
