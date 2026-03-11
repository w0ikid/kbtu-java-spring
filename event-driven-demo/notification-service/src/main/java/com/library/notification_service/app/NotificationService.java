package com.library.notification_service.app;

import com.library.notification_service.domain.Notification;
import com.library.notification_service.domain.NotificationType;
import com.library.notification_service.infra.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${services.member-service.url}")
    private String memberServiceUrl;

    @Transactional
    public void notifyBorrow(UUID memberId, UUID bookId, String bookTitle, LocalDateTime borrowedAt) {
        log.info("Processing borrow notification for member: {}, book: {}", memberId, bookId);
        String email = fetchMemberEmail(memberId);
        
        String subject = "Book Borrowed: " + bookTitle;
        String content = String.format("Hello! You have successfully borrowed '%s' on %s.", bookTitle, borrowedAt);
        
        sendAndSave(memberId, email, subject, content, NotificationType.BORROWED);
    }

    @Transactional
    public void notifyCancel(UUID memberId, UUID bookId, String bookTitle, LocalDateTime cancelledAt) {
        log.info("Processing cancel notification for member: {}, book: {}", memberId, bookId);
        String email = fetchMemberEmail(memberId);
        
        String subject = "Borrow Cancelled: " + bookTitle;
        String content = String.format("Hello! Your borrow for book '%s' has been cancelled on %s.", bookTitle, cancelledAt);
        
        sendAndSave(memberId, email, subject, content, NotificationType.CANCELLED);
    }

    private void sendAndSave(UUID memberId, String email, String subject, String content, NotificationType type) {
        emailService.sendEmail(email, subject, content);
        
        Notification notification = Notification.builder()
                .memberId(memberId)
                .recipient(email)
                .subject(subject)
                .content(content)
                .type(type)
                .sentAt(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
    }

    private String fetchMemberEmail(UUID memberId) {
        try {
            log.info("Fetching email for member {} from {}", memberId, memberServiceUrl);
            Map<String, Object> response = restTemplate.getForObject(memberServiceUrl + "/" + memberId, Map.class);
            if (response != null && response.containsKey("email")) {
                return (String) response.get("email");
            }
        } catch (Exception e) {
            log.error("Failed to fetch member email: {}", e.getMessage());
        }
        return "unknown@library.kz"; // Fallback
    }
}
