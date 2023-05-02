package com.kodit.application.service;

import com.kodit.application.exceptions.ApiExceptionResponse;
import com.kodit.application.exceptions.CustomException;
import com.kodit.application.model.Like;
import com.kodit.application.model.Post;
import com.kodit.application.model.User;
import com.kodit.application.repository.LikeRepository;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.utils.ErrorMessageConstants;
import com.kodit.application.utils.SuccessMessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final UserService userService;
    private final LikeRepository likeRepository;
    private final PostService postService;
    @Transactional
    public ResponseEntity<ResponseWrapper> likePost(Long postId, String username) {
        final User user = userService.findUserByUsername(username);
        final Post post = postService.findPostByPostId(postId);
        likeRepository.findLikeByUser_IdAndPost_Id(user.getId(),postId).ifPresent((like)->{
            throw new CustomException(new ApiExceptionResponse(ErrorMessageConstants.LIKED_THE_POST, HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        });
        Like newLike = Like.builder().post(post).user(user).build();
        Like savedLike = likeRepository.save(newLike);
        post.getLikes().add(savedLike);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.POST_LIKE_SUCCESS));
    }
    @Transactional
    public ResponseEntity<ResponseWrapper> unLikePost(Long postId, String username) {
        final User user = userService.findUserByUsername(username);
        likeRepository.deleteByUserAndPost(user.getId(), postId);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.POST_LIKE_SUCCESS));
    }

}
