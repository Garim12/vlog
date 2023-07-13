package com.sparta.vlog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean liked = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comments_id")
    private Comment comment;

    public Likes(User user, Post post){
        this.user = user;
        this.post = post;
        this.liked = true;
    }

    public Likes(User user, Comment comment){
        this.user = user;
        this.comment = comment;
        this.liked = true;
    }
}