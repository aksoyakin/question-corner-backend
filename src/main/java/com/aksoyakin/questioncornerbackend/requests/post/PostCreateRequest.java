package com.aksoyakin.questioncornerbackend.requests.post;

import lombok.Data;

@Data
public class PostCreateRequest {

    Long id;
    String text;
    String title;
    Long userId;
}
