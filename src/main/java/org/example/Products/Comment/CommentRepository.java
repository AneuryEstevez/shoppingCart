package org.example.Products.Comment;

import org.example.Products.Product;

import java.util.List;

public interface CommentRepository {

    List<Comment> FindComments();

    void UploadComment(Comment comment);

    void DeleteComment(Comment comment);
}
