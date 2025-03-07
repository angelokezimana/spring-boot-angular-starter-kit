package com.angelokezimana.starter.mapper.blog;

import com.angelokezimana.starter.dto.blog.CommentDTO;
import com.angelokezimana.starter.entity.blog.Comment;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {

    public static CommentDTO toCommentDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getText(),
                comment.getPublishedOn(),
                AuthorMapper.toAuthorDTO(comment.getAuthor())
        );
    }

    public static List<CommentDTO> toCommentDTOList(List<Comment> comments) {
        if (comments == null) {
            return Collections.emptyList();
        }

        return comments.stream()
                .map(CommentMapper::toCommentDTO)
                .collect(Collectors.toList());
    }
}
