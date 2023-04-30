package com.kodit.application.controller;

import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/like")
@AllArgsConstructor
public class LikeController {
    private LikeService likeService;
    @PostMapping("")
    public ResponseEntity<ResponseWrapper> likePost(Long postId, Principal principal){
        return likeService.likePost(postId, principal.getName());
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseWrapper> unLikePost(Long postId, Principal principal){
        return likeService.unLikePost(postId, principal.getName());
    }
}
