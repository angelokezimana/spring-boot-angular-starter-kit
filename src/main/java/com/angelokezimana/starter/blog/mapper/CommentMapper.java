package com.angelokezimana.starter.blog.mapper;

import com.angelokezimana.starter.blog.dto.CommentDto;
import com.angelokezimana.starter.blog.model.Comment;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {

    public static CommentDto toCommentDTO(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getPublishedOn(),
                AuthorMapper.toAuthorDTO(comment.getAuthor())
        );
    }

    public static List<CommentDto> toCommentDTOList(List<Comment> comments) {
        if (comments == null) {
            return Collections.emptyList();
        }

        return comments.stream()
                .map(CommentMapper::toCommentDTO)
                .collect(Collectors.toList());
    }
}
