package com.angelokezimana.posta.repository;

import com.angelokezimana.posta.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository  extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM posts WHERE id = ?1", nativeQuery = true)
    Optional<Post> findById(long postId);
}
