package com.kodit.application.mapper;

import com.kodit.application.dto.PostDto;
import com.kodit.application.model.Like;
import com.kodit.application.model.Post;
import com.kodit.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDto convertToPost(Post post);
    @Mapping(source = "postedBy.username", target = "postedBy")
    @Mapping(target = "likes", expression = "java(getLikesCount(post.getLikes()))")
    PostDto convertToPostDto(Post post);

    default String map(User user) {
        return user.getUsername();
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
