package org.example.Products.Comment;

import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class HibernateCommentRepository implements CommentRepository {

    private final EntityManagerFactory entityManagerFactory;

    public HibernateCommentRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Comment> FindComments() {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            List<Comment> comments = entityManager.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
            return comments;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void UploadComment(Comment comment) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(comment);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            entityManager.getTransaction().getRollbackOnly();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void DeleteComment(Comment comment) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Comment entity = entityManager.find(Comment.class, comment.getId());
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            entityManager.getTransaction().getRollbackOnly();
        } finally {
            entityManager.close();
        }
    }
}
