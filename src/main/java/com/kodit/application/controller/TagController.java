package com.kodit.application.controller;

import com.kodit.application.model.Tag;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/tag")
public class TagController {
    private final TagService tagService;

    @PostMapping("")
    public ResponseEntity<ResponseWrapper> followTag(@RequestBody Tag incomingTag, Principal principal) {
        return tagService.followATag(incomingTag, principal.getName());
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseWrapper> unFollowTag(@RequestBody Tag incomingTag, Principal principal) {
        return tagService.unFollowATag(incomingTag, principal.getName());
    }

    @GetMapping
    public ResponseEntity<Boolean> isFollowing(@RequestBody Tag requestTag,Principal principal){
        return tagService.isFollowingTag(requestTag, principal.getName());
    }
    @GetMapping("/all")
    public ResponseEntity<List<Tag>> isFollowing(@RequestParam String tagname){
        return tagService.searchTags(tagname);
    }

}
