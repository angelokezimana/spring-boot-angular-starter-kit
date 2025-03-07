package com.angelokezimana.starter.repository.blog;

import com.angelokezimana.starter.entity.blog.PhotoPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoPostRepository extends JpaRepository<PhotoPost, Long> {
}
