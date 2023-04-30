package com.kodit.application.service;

import com.kodit.application.dto.PostDto;
import com.kodit.application.dto.PostUploadRequestDto;
import com.kodit.application.exceptions.ApiExceptionResponse;
import com.kodit.application.exceptions.CustomException;
import com.kodit.application.mapper.PostMapper;
import com.kodit.application.model.Post;
import com.kodit.application.model.Tag;
import com.kodit.application.model.User;
import com.kodit.application.repository.PostRepository;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.utils.ErrorMessageConstants;
import com.kodit.application.utils.SuccessMessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final UserService userService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public ResponseEntity<ResponseWrapper> uploadPost(PostUploadRequestDto postUploadRequestDto, String username) {
        final User user = userService.findUserByUsername(username);
        Post newPost = Post.builder().postedBy(user).content(postUploadRequestDto.getContent()).postedTime(LocalDateTime.now()).build();
        Post savedPost = postRepository.save(newPost);
        user.getPosts().add(savedPost);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.POST_CREATE_SUCCESS));
    }

    public ResponseEntity<ResponseWrapper> deletePost(Long postId, String username) {
        final User user = userService.findUserByUsername(username);
        if (!postRepository.existsByIdAndPostedBy_Id(postId, user.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper(ErrorMessageConstants.POST_DOES_NOT_EXIST));
        }
        postRepository.deleteById(postId);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.POST_DELETE_SUCCESS));
    }

    public ResponseEntity<List<PostDto>> getAllPostsOfaUsers(String username) {
        final User user = userService.findUserByUsername(username);
        List<Post> posts = postRepository.findAllByPostedBy_Id(user.getId());
        List<PostDto> postDtos = postMapper.mapPostsToPostDtos(posts);
        return ResponseEntity.ok(postDtos);
    }

    public ResponseEntity<List<Post>> getAllPostsForaUser(String username) {
        final User user = userService.findUserByUsername(username);
        return ResponseEntity.ok(postRepository.findPostsByFriendsButNotByUser(user.getId()));
    }

    protected Post findPostByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomException(
                new ApiExceptionResponse(ErrorMessageConstants.POST_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST, LocalDateTime.now())
        ));
    }

    public ResponseEntity<List<Post>> getAllPostsOfTag(Long tagId){
        return ResponseEntity.ok(postRepository.findPostsByTagId(tagId));
    }

}

