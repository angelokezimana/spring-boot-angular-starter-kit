package com.angelokezimana.starter.mapper.blog;

import com.angelokezimana.starter.dto.blog.PostDTO;
import com.angelokezimana.starter.dto.blog.PostDetailDTO;
import com.angelokezimana.starter.entity.blog.Post;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class PostMapper {

    public static PostDetailDTO toPostDetailDTO(Post post) {
        return new PostDetailDTO(
                post.getId(),
                post.getTitle(),
                post.getText(),
                post.getImageCoverByte(),
                post.getPublishedOn(),
                (post.getComments() != null && !post.getComments().isEmpty()) ? post.getComments().size() : 0,
                PhotoPostMapper.toPhotoPostDTOList(post.getPhotoPosts()),
                AuthorMapper.toAuthorDTO(post.getAuthor())
        );
    }

    public static PostDTO toPostDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getText(),
                post.getImageCoverByte(),
                post.getPublishedOn(),
                (post.getComments() != null && !post.getComments().isEmpty()) ? post.getComments().size() : 0,
                AuthorMapper.toAuthorDTO(post.getAuthor())
        );
    }

    public static Set<PostDTO> toPostDTOList(Set<Post> posts) {
        if (posts == null) {
            return Collections.emptySet();
        }

        return posts.stream()
                .map(PostMapper::toPostDTO)
                .collect(Collectors.toSet());
    }
}
