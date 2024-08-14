package com.angelokezimana.posta.repository.blog;

import com.angelokezimana.posta.entity.blog.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
