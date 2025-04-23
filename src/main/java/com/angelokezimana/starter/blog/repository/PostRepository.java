package com.angelokezimana.starter.blog.repository;

import com.angelokezimana.starter.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
//    @Query("SELECT p FROM Post p " +
//            "LEFT JOIN FETCH p.comments c " +
//            "LEFT JOIN FETCH p.author u " +
//            "LEFT JOIN FETCH p.photoPosts pp")
//    Page<Post> findAllPostsWithRelations(Pageable pageable);
}
