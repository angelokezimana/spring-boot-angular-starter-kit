package com.angelokezimana.starter.blog.mapper;

import com.angelokezimana.starter.blog.dto.CommentWithPostDTO;
import com.angelokezimana.starter.blog.model.Comment;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class CommentWithPostMapper {

    public static CommentWithPostDTO toCommentWithPostDTO(Comment comment) {
        return new CommentWithPostDTO(
                comment.getId(),
                comment.getText(),
                comment.getPublishedOn(),
                PostMapper.toPostDTO(comment.getPost()),
                AuthorMapper.toAuthorDTO(comment.getAuthor())
        );
    }

    public static Set<CommentWithPostDTO> toCommentWithPostDTOList(Set<Comment> comments) {
        if (comments == null) {
            return Collections.emptySet();
        }

        return comments.stream()
                .map(CommentWithPostMapper::toCommentWithPostDTO)
                .collect(Collectors.toSet());
    }
}
