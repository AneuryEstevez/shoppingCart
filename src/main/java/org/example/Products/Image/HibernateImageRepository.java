package org.example.Products.Image;

import jakarta.persistence.EntityManagerFactory;
import org.example.Products.Comment.Comment;

import java.util.List;

public class HibernateImageRepository implements ImageRepository{
    private final EntityManagerFactory entityManagerFactory;

    public HibernateImageRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Image> FindImages() {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            List<Image> images = entityManager.createQuery("SELECT i FROM Image i", Image.class).getResultList();
            return images;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void SaveImage(Image image) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(image);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            entityManager.getTransaction().getRollbackOnly();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void DeleteImage(Image image) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(image);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            entityManager.getTransaction().getRollbackOnly();
        } finally {
            entityManager.close();
        }
    }
}
