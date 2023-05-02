package com.kodit.application.mapper;

import com.kodit.application.dto.CommentDto;
import com.kodit.application.dto.PostDto;
import com.kodit.application.model.Comment;
import com.kodit.application.model.Like;
import com.kodit.application.model.Post;
import com.kodit.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDto convertToPost(Post post);

    @Mapping(source = "postedBy.username", target = "postedBy")
    @Mapping(target = "likes", expression = "java(getLikesCount(post.getLikes()))")
    @Mapping(target = "comments",source = "comments",qualifiedByName ="convertToCommentDtoList" )
    PostDto convertToPostDto(Post post);


    default String map(User user) {
        return user.getUsername();
    }
    @Named("convertToCommentDtoList")
    default List<CommentDto> convertToCommentDtoList(List<Comment> comments){
        return comments.stream().map(CommentMapper.INSTANCE::CommentToCommentDto).collect(Collectors.toList());
    }

    default Long getLikesCount(List<Like> likes) {
        return (long) likes.size();
    }

    default List<PostDto> mapPostsToPostDtos(List<Post> posts) {
        return posts.stream()
                .map(this::convertToPostDto)
                .collect(Collectors.toList());
    }
}
