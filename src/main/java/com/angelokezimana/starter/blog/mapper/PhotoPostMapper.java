package com.angelokezimana.starter.blog.mapper;

import com.angelokezimana.starter.blog.dto.PhotoPostDTO;
import com.angelokezimana.starter.blog.model.PhotoPost;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class PhotoPostMapper {

    public static PhotoPostDTO toPhotoPostDTO(PhotoPost photoPost) {
        return new PhotoPostDTO(
                photoPost.getId(),
                photoPost.getImageByte()
        );
    }

    public static Set<PhotoPostDTO> toPhotoPostDTOList(Set<PhotoPost> photoPosts) {
        if (photoPosts == null) {
            return Collections.emptySet();
        }

        return photoPosts.stream()
                .map(PhotoPostMapper::toPhotoPostDTO)
                .collect(Collectors.toSet());
    }
}
