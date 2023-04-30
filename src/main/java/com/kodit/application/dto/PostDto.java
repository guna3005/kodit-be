package com.kodit.application.dto;

import com.kodit.application.model.Tag;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private LocalDateTime postedTime;
    private List<CommentDto> comments;
    private Long likes;
    private String content;
    private List<Tag> tags;
    private String postedBy;
}
