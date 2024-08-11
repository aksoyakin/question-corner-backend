package com.aksoyakin.questioncornerbackend.controllers;

import com.aksoyakin.questioncornerbackend.entities.Like;
import com.aksoyakin.questioncornerbackend.requests.like.LikeCreateRequest;
import com.aksoyakin.questioncornerbackend.responses.LikeResponse;
import com.aksoyakin.questioncornerbackend.services.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(final LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> postId, @RequestParam Optional<Long> userId) {
        return likeService.getAllLikes(postId, userId);
    }

    @GetMapping("/{likeId}")
    public Like getLikeById(@PathVariable Long likeId) {
        return likeService.getLikeById(likeId);
    }

    @PostMapping
    public Like createLike(@RequestBody LikeCreateRequest likeCreateRequest) {
        return likeService.createLike(likeCreateRequest);
    }

    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable Long likeId) {
        likeService.deleteLike(likeId);
    }
}
