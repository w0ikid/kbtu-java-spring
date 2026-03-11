package com.library.member_service.infra;

import com.library.member_service.domain.MemberBorrowHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MemberBorrowHistoryRepository extends JpaRepository<MemberBorrowHistory, UUID> {
}
