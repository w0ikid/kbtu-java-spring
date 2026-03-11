package com.library.member_service.api.mapper;

import com.library.member_service.api.dto.MemberRequest;
import com.library.member_service.api.dto.MemberResponse;
import com.library.member_service.domain.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "borrowedBooksCount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Member toEntity(MemberRequest request);

    MemberResponse toResponse(Member member);
}
