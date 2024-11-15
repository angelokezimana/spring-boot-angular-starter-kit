package com.angelokezimana.posta.mapper.blog;

import com.angelokezimana.posta.dto.blog.PostDTO;
import com.angelokezimana.posta.entity.blog.Post;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class PostMapper {

    public static PostDTO toPostDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getText(),
                post.getPublishedOn(),
                CommentMapper.toCommentDTOList(post.getComments()),
                PhotoPostMapper.toPhotoPostDTOList(post.getPhotoPosts()),
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
