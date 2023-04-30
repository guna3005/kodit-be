package com.kodit.application.controller;

import com.kodit.application.dto.CommentDto;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequiredArgsConstructor
@RequestMapping("api/v1/comment")
public class CommentController {

    private CommentService commentService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> commentOnPost(@RequestBody CommentDto commentDto, Principal principal) {
        return commentService.commentOnPost(commentDto, principal.getName());
    }
}
