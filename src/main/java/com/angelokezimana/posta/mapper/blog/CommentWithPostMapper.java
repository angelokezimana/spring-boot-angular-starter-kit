package com.angelokezimana.posta.mapper.blog;

import com.angelokezimana.posta.dto.blog.CommentWithPostDTO;
import com.angelokezimana.posta.entity.blog.Comment;

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
