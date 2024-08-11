package com.aksoyakin.questioncornerbackend.requests.comment;

import lombok.Data;

@Data
public class CommentCreateRequest {
    Long postId;
    Long userId;
    String text;
}
