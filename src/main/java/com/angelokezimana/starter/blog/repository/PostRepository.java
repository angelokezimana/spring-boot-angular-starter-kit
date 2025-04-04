package com.angelokezimana.starter.blog.repository;

import com.angelokezimana.starter.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
