package com.kodit.application.repository;

import com.kodit.application.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsByIdAndPostedBy_Id(Long postId, Long userId);

    List<Post> findAllByPostedBy_Id(Long userId);

    @Query("SELECT p FROM Post p JOIN p.postedBy u WHERE u.id IN "
            + "(SELECT CASE WHEN r.requestedTo.id = :userId THEN r.requestedBy.id ELSE r.requestedTo.id END "
            + "FROM UserRequest r WHERE r.status = 'ACCEPTED' AND (r.requestedBy.id = :userId OR r.requestedTo.id = :userId))" //)
            + "AND u.id <> :userId")
    List<Post> findAllPostsByFriendsAndStatusAcceptedExcludingUser(@Param("userId") Long userId);

    @Query("SELECT p FROM Post p " +
            "JOIN p.postedBy u " +
            "JOIN u.friendList f " +
            "WHERE u.id = :userId " +
            "AND p.postedBy <> u " +
            "AND p.postedBy NOT IN (SELECT f1 FROM u.friendList f1 WHERE f1.id = p.postedBy.id)")
    List<Post> findPostsByFriendsButNotByUser(@Param("userId") Long userId);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.id = :tagId")
    List<Post> findPostsByTagId(@Param("tagId") Long tagId);

}
