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

    //좋아요 누른 사람   좋아요 n:1 유저
    @ManyToOne
    @JoinColumn(name = "users")
    private User user;

    //좋아요가 눌린 게시글  좋아요 n:1 게시글
    @ManyToOne
    @JoinColumn(name = "posts")
    private Post post;

    public Likes(Post post, User user){
        this.post = post;
        this.user = user;
    }
}
