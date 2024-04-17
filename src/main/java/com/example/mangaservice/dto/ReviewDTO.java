package com.example.mangaservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private String content;

    public ReviewDTO(String content)
    {
        this.content = content;
    }
}
