package com.aksoyakin.questioncornerbackend.services;

import com.aksoyakin.questioncornerbackend.entities.Like;
import com.aksoyakin.questioncornerbackend.entities.Post;
import com.aksoyakin.questioncornerbackend.entities.User;
import com.aksoyakin.questioncornerbackend.repos.PostRepository;
import com.aksoyakin.questioncornerbackend.requests.post.PostCreateRequest;
import com.aksoyakin.questioncornerbackend.requests.post.PostUpdateRequest;
import com.aksoyakin.questioncornerbackend.responses.LikeResponse;
import com.aksoyakin.questioncornerbackend.responses.PostResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {


    private final PostRepository postRepository;
    private  LikeService likeService;
    private final UserService userService;


    public PostService(PostRepository postRepository, UserService userService){
        this.postRepository = postRepository;
        this.userService = userService;
    }


    @Autowired
    public void setLikeService(@Lazy LikeService likeService) {
        this.likeService = likeService;
    }




    public List<PostResponse> getAllPosts(Optional<Long> userId){
        List<Post> list;
        if(userId.isPresent()) {
            list = postRepository.findByUserId(userId.get());
        } else {
            list = postRepository.findAll();
        }
        return list.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLikes(Optional.of(p.getId()),  Optional.ofNullable(null));
            return new PostResponse(p, likes);}).collect(Collectors.toList());

    }

    public Post createPost(PostCreateRequest newPostRequest){
        User user = userService.getUserById(newPostRequest.getUserId());
        if(user == null) {
            return null;
        }
        Post post = new Post();
        post.setTitle(newPostRequest.getTitle());
        post.setText(newPostRequest.getText());
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post getPostById(Long postId){
        return postRepository.findById(postId).orElse(null);
    }

    public Post updatePost(Long postId, PostUpdateRequest updatePost){
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post foundPost = post.get();
            foundPost.setTitle(updatePost.getTitle());
            foundPost.setText(updatePost.getText());
            postRepository.save(foundPost);
            return foundPost;
        }else{
            return null;
        }
    }

    public void deleteById(Long postId){
        postRepository.deleteById(postId);
    }
}
