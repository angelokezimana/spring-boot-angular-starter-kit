package com.angelokezimana.posta.repository;

import com.angelokezimana.posta.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
