package com.library.book_service.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

import com.library.book_service.domain.BorrowRecord;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, UUID> {
    List<BorrowRecord> findAllByMemberId(UUID memberId);
    Optional<BorrowRecord> findByBookIdAndMemberIdAndReturnedAtIsNull(UUID bookId, UUID memberId);
}