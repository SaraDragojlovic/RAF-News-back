package com.example.demo.Repositories.Comment;

import com.example.demo.Entities.Comment;

import java.util.List;

public interface CommentRepository {

    Comment addComment(Comment comment);
    List<Comment> allComments();
    Comment findComment(Integer id);
    List<Comment> findCommentsByNewsId(Integer id);
    Comment updateComment(Comment comment);
    void deleteComment(Integer id);
}
