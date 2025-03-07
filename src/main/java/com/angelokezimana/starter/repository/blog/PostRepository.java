package com.angelokezimana.starter.repository.blog;

import com.angelokezimana.starter.entity.blog.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
