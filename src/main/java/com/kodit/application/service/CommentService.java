package com.kodit.application.service;

import com.kodit.application.dto.CommentDto;
import com.kodit.application.model.Comment;
import com.kodit.application.model.Post;
import com.kodit.application.model.User;
import com.kodit.application.repository.CommentRepository;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.utils.SuccessMessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PostService postService;

    @Transactional
    public ResponseEntity<ResponseWrapper> commentOnPost(CommentDto commentDto, String username) {
        final User user = userService.findUserByUsername(username);
        final Post post = postService.findPostByPostId(commentDto.getPostId());
        Comment newComment = Comment.builder().commentedOn(LocalDateTime.now()).text(commentDto.getText()).user(user).post(post).build();
        commentRepository.save(newComment);
        post.getComments().add(newComment);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.COMMENT_POST_SUCCESS));
    }
}
