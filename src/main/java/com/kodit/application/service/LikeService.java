package com.kodit.application.service;

import com.kodit.application.model.Like;
import com.kodit.application.model.Post;
import com.kodit.application.model.User;
import com.kodit.application.repository.LikeRepository;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.utils.SuccessMessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final UserService userService;
    private final LikeRepository likeRepository;
    private final PostService postService;

    public ResponseEntity<ResponseWrapper> likePost(Long postId, String username) {
        final User user = userService.findUserByUsername(username);
        final Post post = postService.findPostByPostId(postId);
        Like newLike = Like.builder().post(post).user(user).build();
        likeRepository.save(newLike);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.POST_LIKE_SUCCESS));
    }

    public ResponseEntity<ResponseWrapper> unLikePost(Long postId, String username) {
        final User user = userService.findUserByUsername(username);
        likeRepository.deleteByUserAndPost(user.getId(), postId);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.POST_LIKE_SUCCESS));
    }

}
