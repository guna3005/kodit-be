package com.kodit.application.BootStrap;

import com.kodit.application.model.*;
import com.kodit.application.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
//@Component
public class bootStrap implements CommandLineRunner {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        Tag tag1 = Tag.builder().name("tag1").build();
        Tag tag2 = Tag.builder().name("tag2").build();
        Tag tag3 = Tag.builder().name("tag3").build();

        List<Tag> tags = Arrays.asList(tag1, tag2, tag3);
        tagRepository.saveAll(tags);

        User user1 = User.builder()
                .userRole(UserRole.USER)
                .email("user1@example.com")
                .password(bCryptPasswordEncoder.encode("password1"))
                .name("User1")
                .friendList(new HashSet<>())
                .build();
        User user2 = User.builder()
                .userRole(UserRole.USER)
                .email("user2@example.com")
                .password(bCryptPasswordEncoder.encode("password2"))
                .name("User2")
                .friendList(new HashSet<>())
                .build();
        User user3 = User.builder()
                .userRole(UserRole.USER)
                .email("user3@example.com")
                .password(bCryptPasswordEncoder.encode("password3"))
                .name("User3")
                .friendList(new HashSet<>())
                .build();

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        User savedUser3 = userRepository.save(user3);

// Making them friends
        user1.addFriend(user2);
        user2.addFriend(user3);
        user3.addFriend(user1);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        for (int i = 1; i <= 5; i++) {
            Post postUser1 = createPost(savedUser1, "Content of post " + i + " by User1", tags);
            Post postUser2 = createPost(savedUser2, "Content of post " + i + " by User2", tags);
            Post postUser3 = createPost(savedUser3, "Content of post " + i + " by User3", tags);

            postUser1 = postRepository.save(postUser1);
            postUser2 = postRepository.save(postUser2);
            postUser3 = postRepository.save(postUser3);
            // Creating and saving comments and likes for each post
            Comment commentUser1 = createComment(savedUser1, postUser1, "Comment " + i + " on User1's post");
            Comment commentUser2 = createComment(savedUser2, postUser2, "Comment " + i + " on User2's post");
            Comment commentUser3 = createComment(savedUser3, postUser3, "Comment " + i + " on User3's post");

            commentRepository.save(commentUser1);
            commentRepository.save(commentUser2);
            commentRepository.save(commentUser3);
            Like likeUser1 = createLike(savedUser1, postUser1);
            Like likeUser2 = createLike(savedUser2, postUser2);
            Like likeUser3 = createLike(savedUser3, postUser3);

            likeRepository.save(likeUser1);
            likeRepository.save(likeUser2);
            likeRepository.save(likeUser3);
        }
    }

    private Post createPost(User user, String content, List<Tag> tags) {
        Post post = Post.builder()
                .postedBy(user)
                .content(content)
                .postedTime(LocalDateTime.now())
                .tags(tags)
                .comments(new ArrayList<>())
                .likes(new ArrayList<>())
                .build();
        return post;
    }

    private Comment createComment(User user, Post post, String text) {
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .commentedOn(LocalDateTime.now())
                .text(text)
                .build();
        return comment;
    }

    private Like createLike(User user, Post post) {
        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();
        return like;
    }
}


