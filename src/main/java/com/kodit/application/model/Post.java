package com.kodit.application.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User postedBy;
    private LocalDateTime postedTime;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "like_id")
    )
    private List<Like> likes;
    @OneToMany
    private List<Tag> tags;
    @Column(columnDefinition = "TEXT")
    private String content;
}
