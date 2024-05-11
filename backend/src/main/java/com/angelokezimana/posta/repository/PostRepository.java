package com.angelokezimana.posta.repository;

import com.angelokezimana.posta.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
