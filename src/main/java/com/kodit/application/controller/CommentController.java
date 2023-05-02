package com.kodit.application.controller;

import com.kodit.application.dto.CommentDto;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
//@RequiredArgsConstructor
@RequestMapping("api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> commentOnPost(@RequestBody CommentDto commentDto, @RequestParam("postid") Long postId, Principal principal) {
        return commentService.commentOnPost(commentDto,postId, principal.getName());
    }
}
