package com.angelokezimana.posta.repository.blog;

import com.angelokezimana.posta.entity.blog.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
