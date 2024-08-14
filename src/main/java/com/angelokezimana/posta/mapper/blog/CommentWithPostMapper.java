package com.angelokezimana.posta.mapper.blog;

import com.angelokezimana.posta.dto.blog.CommentWithPostDTO;
import com.angelokezimana.posta.entity.blog.Comment;

import java.util.List;
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

    public static List<CommentWithPostDTO> toCommentWithPostDTOList(List<Comment> comments) {
        return comments.stream()
                .map(CommentWithPostMapper::toCommentWithPostDTO)
                .collect(Collectors.toList());
    }
}
