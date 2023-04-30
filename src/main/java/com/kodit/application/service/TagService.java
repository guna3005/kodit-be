package com.kodit.application.service;

import com.kodit.application.exceptions.ApiExceptionResponse;
import com.kodit.application.exceptions.CustomException;
import com.kodit.application.model.Post;
import com.kodit.application.model.Tag;
import com.kodit.application.model.User;
import com.kodit.application.repository.TagRepository;
import com.kodit.application.security.dto.ResponseWrapper;
import com.kodit.application.utils.ErrorMessageConstants;
import com.kodit.application.utils.SuccessMessageConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final UserService userService;

    @Transactional
    public ResponseEntity<ResponseWrapper> followATag(Tag incomingTag, String username) {
        final User user = userService.findUserByUsername(username);
        tagRepository.findById(incomingTag.getId()).ifPresentOrElse(tag -> {
            user.getTags().add(tag);
        }, () -> {
            Tag newTag = Tag.builder()
                    .name(incomingTag.getName())
                    .build();
            Tag savedTag = tagRepository.save(newTag);
            user.getTags().add(savedTag);
        });
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.TAG_FOLLOW_SUCCESS));
    }

    @Transactional
    public ResponseEntity<ResponseWrapper> unFollowATag(Tag incomingTag, String username) {
        final User user = userService.findUserByUsername(username);
        Tag savedTag = tagRepository.findById(incomingTag.getId()).orElseThrow(() -> new CustomException(
                new ApiExceptionResponse(ErrorMessageConstants.TAG_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST, LocalDateTime.now())
        ));
        user.getTags().remove(savedTag);
        return ResponseEntity.ok(new ResponseWrapper(SuccessMessageConstants.TAG_UNFOLLOW_SUCCESS));
    }

    public ResponseEntity<List<Post>> getAllPostsOfTag

    @Transactional
    public ResponseEntity<Boolean> isFollowingTag(Tag requestTag,String username){
        final User user = userService.findUserByUsername(username);
        return ResponseEntity.ok(user.getTags().contains(requestTag));
    }

    public ResponseEntity<List<Tag>> searchTags(String searchText){
        return ResponseEntity.ok(tagRepository.findAllByNameContainsIgnoreCase(searchText));
    }


}
