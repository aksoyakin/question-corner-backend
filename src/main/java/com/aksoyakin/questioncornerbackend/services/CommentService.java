package com.aksoyakin.questioncornerbackend.services;

import com.aksoyakin.questioncornerbackend.entities.Comment;
import com.aksoyakin.questioncornerbackend.entities.Post;
import com.aksoyakin.questioncornerbackend.entities.User;
import com.aksoyakin.questioncornerbackend.repos.CommentRepository;
import com.aksoyakin.questioncornerbackend.requests.comment.CommentCreateRequest;
import com.aksoyakin.questioncornerbackend.requests.comment.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllComments(Optional<Long> postId, Optional<Long> userId) {
        if(postId.isPresent() && userId.isPresent()) {
            return commentRepository.findByPostIdAndUserId(postId.get(), userId.get());
        }else if(userId.isPresent()) {
            return commentRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        }else{
            return commentRepository.findAll();
        }
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createComment(CommentCreateRequest commentCreateRequest) {
        User user = userService.getUserById(commentCreateRequest.getUserId());
        Post post = postService.getPostById(commentCreateRequest.getPostId());
        if(user == null || post == null){
            return null;
        }
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText(commentCreateRequest.getText());
        return commentRepository.save(comment);
    }


    public Comment updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()) {
            Comment foundComment = comment.get();
            foundComment.setText(commentUpdateRequest.getText());
            return commentRepository.save(foundComment);
        }else{
            return null;
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
