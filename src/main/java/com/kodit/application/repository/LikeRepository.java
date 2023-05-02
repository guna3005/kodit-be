package com.kodit.application.repository;

import com.kodit.application.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Modifying
    @Query("DELETE FROM Like l WHERE l.user.id = ?1 AND l.post.id = ?2")
    void deleteByUserAndPost(Long userId, Long postId);

    Optional<Like> findLikeByUser_IdAndPost_Id(Long userId, Long postId);
}
