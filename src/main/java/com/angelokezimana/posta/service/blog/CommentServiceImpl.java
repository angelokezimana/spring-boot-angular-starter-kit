package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.repository.blog.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;
}
