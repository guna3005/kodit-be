package com.kodit.application.controller;

import com.kodit.application.dto.PostDto;
import com.kodit.application.dto.PostUploadRequestDto;
import com.kodit.application.model.Post;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/post")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @GetMapping("/feed")
    public ResponseEntity<List<Post>> getFeed(Principal principal) {
        return postService.getAllPostsForaUser(principal.getName());
    }

    @PostMapping("")
    public ResponseEntity<ResponseWrapper> uploadPost(@RequestBody PostUploadRequestDto postUploadRequestDto, Principal principal) {
        return postService.uploadPost(postUploadRequestDto, principal.getName());
    }

    @GetMapping("")
    public ResponseEntity<List<PostDto>> getAllPosts(Principal principal) {
        return postService.getAllPostsOfaUsers(principal.getName());
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseWrapper> deletePost(@RequestBody Long postId, Principal principal) {
        return postService.deletePost(postId, principal.getName());
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Post>> getAllPostsofTag(@RequestBody Long tagId,Principal principal){
        return postService.getAllPostsOfTag(tagId);
    }

}
