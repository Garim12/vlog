// PostDto.java
package com.sparta.vlog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String username;
    private String password;
    private String content;
}
