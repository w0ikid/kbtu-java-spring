package com.library.book_service.infra;

import com.library.book_service.domain.Book;
import com.library.book_service.domain.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Page<Book> findAllByStatus(BookStatus status, Pageable pageable);
}
