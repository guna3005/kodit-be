package com.kodit.application.mapper;

import com.kodit.application.dto.CommentDto;
import com.kodit.application.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    @Mapping(target = "commentedBy",source = "user.username")
    CommentDto CommentToCommentDto(Comment comment);


}
