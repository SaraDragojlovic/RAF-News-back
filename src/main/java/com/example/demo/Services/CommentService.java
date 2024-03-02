package com.example.demo.Services;

import com.example.demo.Entities.Comment;
import com.example.demo.Repositories.Comment.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return this.commentRepository.addComment(comment);
    }
    public List<Comment> allComments() {
        return this.commentRepository.allComments();
    }
    public Comment findComment(Integer id) {
        return this.commentRepository.findComment(id);
    }
    public List<Comment> findCommentsByNewsId(Integer id) {
        return this.commentRepository.findCommentsByNewsId(id);
    }
    public Comment updateComment(Comment comment) {
        return this.commentRepository.updateComment(comment);
    }
    public void deleteComment(Integer id) {
        this.commentRepository.deleteComment(id);
    }
}
