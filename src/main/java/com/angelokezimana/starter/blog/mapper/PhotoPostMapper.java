package com.angelokezimana.starter.blog.mapper;

import com.angelokezimana.starter.blog.dto.PhotoPostDto;
import com.angelokezimana.starter.blog.model.PhotoPost;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class PhotoPostMapper {

    public static PhotoPostDto toPhotoPostDTO(PhotoPost photoPost) {
        return new PhotoPostDto(
                photoPost.getId(),
                photoPost.getImageByte()
        );
    }

    public static Set<PhotoPostDto> toPhotoPostDTOList(Set<PhotoPost> photoPosts) {
        if (photoPosts == null) {
            return Collections.emptySet();
        }

        return photoPosts.stream()
                .map(PhotoPostMapper::toPhotoPostDTO)
                .collect(Collectors.toSet());
    }
}
