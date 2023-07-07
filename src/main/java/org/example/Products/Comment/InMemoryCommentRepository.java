package org.example.Products.Comment;

import org.example.Products.Product;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryCommentRepository implements CommentRepository {

    private final List<Comment> comments;

    public InMemoryCommentRepository() {
        this.comments = new CopyOnWriteArrayList<>();
    }

    @Override
    public List<Comment> FindComments() {
        return comments;
    }

    @Override
    public void UploadComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public void DeleteComment(Comment comment) {
        comments.remove(comment);
    }
}
