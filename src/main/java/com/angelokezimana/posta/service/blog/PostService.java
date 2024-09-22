package com.angelokezimana.posta.service.blog;

import com.angelokezimana.posta.dto.blog.PostDTO;
import com.angelokezimana.posta.dto.blog.PostRequestDTO;
import com.angelokezimana.posta.dto.blog.PostRequestUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    Page<PostDTO> getAllPosts(Pageable pageable);

    PostDTO createPost(PostRequestDTO postRequestDTO, List<MultipartFile> photos) throws IOException;

    PostDTO getPost(Long postId);

    PostDTO updatePost(Long postId, PostRequestUpdateDTO postRequestDTO);

    void deletePost(Long postId);
}
