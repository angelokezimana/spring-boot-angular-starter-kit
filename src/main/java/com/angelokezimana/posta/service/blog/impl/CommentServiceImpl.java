package com.angelokezimana.posta.service.blog.impl;

import com.angelokezimana.posta.repository.blog.CommentRepository;
import com.angelokezimana.posta.service.blog.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
}
