package com.library.member_service.app;

import com.library.member_service.api.dto.MemberRequest;
import com.library.member_service.api.dto.MemberResponse;
import com.library.member_service.api.mapper.MemberMapper;
import com.library.member_service.domain.Member;
import com.library.member_service.domain.MemberBorrowHistory;
import com.library.member_service.infra.MemberBorrowHistoryRepository;
import com.library.member_service.infra.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberBorrowHistoryRepository historyRepository;
    private final MemberMapper memberMapper;

    public MemberResponse createMember(MemberRequest request) {
        log.info("Creating member: {}", request.name());
        Member member = memberMapper.toEntity(request);
        member.setBorrowedBooksCount(0);
        member.setCreatedAt(LocalDateTime.now());
        Member saved = memberRepository.save(member);
        return memberMapper.toResponse(saved);
    }

    public MemberResponse getById(UUID id) {
        return memberRepository.findById(id)
                .map(memberMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Member not found: " + id));
    }

    public Page<MemberResponse> getAll(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(memberMapper::toResponse);
    }

    public MemberResponse updateMember(UUID id, MemberRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found: " + id));
        member.setName(request.name());
        member.setEmail(request.email());
        member.setPhone(request.phone());
        return memberMapper.toResponse(memberRepository.save(member));
    }

    public void deleteMember(UUID id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found: " + id);
        }
        memberRepository.deleteById(id);
    }

    @Transactional
    public void incrementBooksCount(UUID id) {
        log.info("Incrementing books count for member: {}", id);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found: " + id));
        member.setBorrowedBooksCount(member.getBorrowedBooksCount() + 1);
        memberRepository.save(member);
    }

    @Transactional
    public void decrementBooksCount(UUID id) {
        log.info("Decrementing books count for member: {}", id);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found: " + id));
        if (member.getBorrowedBooksCount() > 0) {
            member.setBorrowedBooksCount(member.getBorrowedBooksCount() - 1);
            memberRepository.save(member);
        }
    }

    @Transactional
    public void addBorrowHistory(UUID memberId, UUID bookId, String bookTitle, LocalDateTime borrowedAt) {
        log.info("Adding borrow history for member: {}, book: {}", memberId, bookId);
        MemberBorrowHistory history = MemberBorrowHistory.builder()
                .memberId(memberId)
                .bookId(bookId)
                .bookTitle(bookTitle)
                .borrowedAt(borrowedAt)
                .build();
        historyRepository.save(history);
    }
}
