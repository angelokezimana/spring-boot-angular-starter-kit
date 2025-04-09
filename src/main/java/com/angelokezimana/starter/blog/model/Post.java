package com.angelokezimana.starter.blog.model;

import com.angelokezimana.starter.user.model.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String text;

    @Column(nullable = false)
    private String imageCover;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private Set<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private Set<PhotoPost> photoPosts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", nullable = false)
    private User author;

    @CreationTimestamp
    private LocalDateTime publishedOn;

    @Transient
    private byte[] imageCoverByte;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<PhotoPost> getPhotoPosts() {
        return photoPosts;
    }

    public void setPhotoPosts(Set<PhotoPost> photoPosts) {
        this.photoPosts = photoPosts;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    public byte[] getImageCoverByte() {
        if (imageCover != null && imageCover.length() > 4) {
            try {
                imageCoverByte = Files.readAllBytes(Paths.get(imageCover));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageCoverByte;
    }
}
