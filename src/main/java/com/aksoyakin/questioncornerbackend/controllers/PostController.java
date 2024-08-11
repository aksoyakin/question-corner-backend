package com.aksoyakin.questioncornerbackend.controllers;

import com.aksoyakin.questioncornerbackend.entities.Post;
import com.aksoyakin.questioncornerbackend.requests.post.PostCreateRequest;
import com.aksoyakin.questioncornerbackend.requests.post.PostUpdateRequest;
import com.aksoyakin.questioncornerbackend.responses.PostResponse;
import com.aksoyakin.questioncornerbackend.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }

    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest newPostRequest) {
        return postService.createPost(newPostRequest);
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest updatePost) {
        return postService.updatePost(postId, updatePost);
    }

    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable Long postId) {
        postService.deleteById(postId);
    }
}
