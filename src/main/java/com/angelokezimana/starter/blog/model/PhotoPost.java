package com.angelokezimana.starter.blog.model;

import jakarta.persistence.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Entity
@Table(name = "photo_posts")
public class PhotoPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Transient
    private byte[] imageByte;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public byte[] getImageByte() {
        if (image != null && image.length() > 4) {
            try {
                imageByte = Files.readAllBytes(Paths.get(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageByte;
    }
}
