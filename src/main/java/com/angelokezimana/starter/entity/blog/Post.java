package com.angelokezimana.starter.entity.blog;

import com.angelokezimana.starter.entity.security.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<PhotoPost> photoPosts;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<PhotoPost> getPhotoPosts() {
        return photoPosts;
    }

    public void setPhotoPosts(List<PhotoPost> photoPosts) {
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
