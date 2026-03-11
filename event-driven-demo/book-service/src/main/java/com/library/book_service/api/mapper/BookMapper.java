package com.library.book_service.api.mapper;

import com.library.book_service.api.dto.BookRequest;
import com.library.book_service.api.dto.BookResponse;
import com.library.book_service.domain.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookResponse toResponse(Book book);
    Book toEntity(BookRequest request);
}