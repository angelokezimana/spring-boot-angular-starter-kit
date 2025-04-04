package com.angelokezimana.starter.blog.repository;

import com.angelokezimana.starter.blog.model.PhotoPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoPostRepository extends JpaRepository<PhotoPost, Long> {
}
