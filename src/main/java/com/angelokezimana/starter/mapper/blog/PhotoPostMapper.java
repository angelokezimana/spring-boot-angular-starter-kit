package com.angelokezimana.starter.mapper.blog;

import com.angelokezimana.starter.dto.blog.PhotoPostDTO;
import com.angelokezimana.starter.entity.blog.PhotoPost;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PhotoPostMapper {

    public static PhotoPostDTO toPhotoPostDTO(PhotoPost photoPost) {
        return new PhotoPostDTO(
                photoPost.getId(),
                photoPost.getImageByte()
        );
    }

    public static List<PhotoPostDTO> toPhotoPostDTOList(List<PhotoPost> photoPosts) {
        if (photoPosts == null) {
            return Collections.emptyList();
        }

        return photoPosts.stream()
                .map(PhotoPostMapper::toPhotoPostDTO)
                .collect(Collectors.toList());
    }
}
