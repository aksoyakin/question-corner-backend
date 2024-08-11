package com.aksoyakin.questioncornerbackend.requests.like;

import lombok.Data;

@Data
public class LikeCreateRequest {
    Long postId;
    Long userId;
}
