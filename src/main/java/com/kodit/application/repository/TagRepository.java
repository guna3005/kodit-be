package com.kodit.application.repository;

import com.kodit.application.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByNameContainsIgnoreCase(String name);
}
