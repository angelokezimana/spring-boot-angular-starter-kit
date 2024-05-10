package com.angelokezimana.posta.model;


import jakarta.persistence.*;

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
}
