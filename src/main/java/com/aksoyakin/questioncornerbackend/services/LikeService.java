package com.aksoyakin.questioncornerbackend.services;

import com.aksoyakin.questioncornerbackend.entities.Like;
import com.aksoyakin.questioncornerbackend.entities.Post;
import com.aksoyakin.questioncornerbackend.entities.User;
import com.aksoyakin.questioncornerbackend.repos.LikeRepository;
import com.aksoyakin.questioncornerbackend.requests.like.LikeCreateRequest;
import com.aksoyakin.questioncornerbackend.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikes(Optional<Long> postId, Optional<Long> userId) {
        List<Like> list;
        if(postId.isPresent() && userId.isPresent()) {
            list = likeRepository.findByPostIdAndUserId(postId.get(), userId.get());
        }else if(userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            list = likeRepository.findByPostId(postId.get());
        }else{
            list = likeRepository.findAll();
        }
        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like getLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createLike(LikeCreateRequest likeCreateRequest) {
        User user = userService.getUserById(likeCreateRequest.getUserId());
        Post post = postService.getPostById(likeCreateRequest.getPostId());
        if(user == null || post == null) {
            return null;
        }
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        return likeRepository.save(like);
    }

    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
