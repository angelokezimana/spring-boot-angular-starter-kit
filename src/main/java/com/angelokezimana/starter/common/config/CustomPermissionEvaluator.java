package com.angelokezimana.starter.common.config;

import com.angelokezimana.starter.blog.model.Comment;
import com.angelokezimana.starter.blog.model.Post;
import com.angelokezimana.starter.user.model.User;
import com.angelokezimana.starter.blog.repository.CommentRepository;
import com.angelokezimana.starter.blog.repository.PostRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CustomPermissionEvaluator(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        if (authentication == null || !(targetDomainObject instanceof String) || !(permission instanceof String)) {
            return false;
        }

        User user = (User) authentication.getPrincipal();

        String resource = targetDomainObject.toString();
        String action = permission.toString();

        // Check if the user has a role with the required permission
        return user.getRoles().stream().anyMatch(role ->
                role.getPermissions().stream().anyMatch(perm ->
                        resource.equals(perm.getResource()) && action.equals(perm.getAction())
                )
        );
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication == null || !(targetId instanceof Long) || targetType == null || permission == null) {
            return false;
        }

        User user = (User) authentication.getPrincipal();

        return switch (targetType.toUpperCase()) {
            case "POST" -> isPostAuthor(user, targetId);
            case "COMMENT" -> isCommentAuthor(user, targetId);
            default -> false;
        };
    }

    private boolean isPostAuthor(User user, Serializable targetId) {
        Long postId = (Long) targetId;

        Optional<Post> optionalPost = postRepository.findById(postId);

        return optionalPost.isPresent() && optionalPost.get().getAuthor().equals(user);
    }

    private boolean isCommentAuthor(User user, Serializable targetId) {
        Long commentId = (Long) targetId;

        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        return optionalComment.isPresent() && optionalComment.get().getAuthor().equals(user);
    }
}
