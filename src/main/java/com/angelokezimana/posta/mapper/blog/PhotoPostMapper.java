package com.angelokezimana.posta.mapper.blog;

import com.angelokezimana.posta.dto.blog.PhotoPostDTO;
import com.angelokezimana.posta.entity.blog.PhotoPost;

import java.util.List;
import java.util.stream.Collectors;

public class PhotoPostMapper {

    public static PhotoPostDTO toPhotoPostDTO(PhotoPost photoPost) {
        return new PhotoPostDTO(
                photoPost.getId(),
                photoPost.getImage()
        );
    }

    public static List<PhotoPostDTO> toPhotoPostDTOList(List<PhotoPost> photoPosts) {
        return photoPosts.stream()
                .map(PhotoPostMapper::toPhotoPostDTO)
                .collect(Collectors.toList());
    }
}
