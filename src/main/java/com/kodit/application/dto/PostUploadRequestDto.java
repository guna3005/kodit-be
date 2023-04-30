package com.kodit.application.dto;

import com.kodit.application.model.Tag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUploadRequestDto {
    private String content;
    private List<Tag> tags;
}
