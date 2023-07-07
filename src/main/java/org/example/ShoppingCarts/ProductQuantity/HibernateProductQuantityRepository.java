package org.example.ShoppingCarts.ProductQuantity;

import jakarta.persistence.EntityManagerFactory;
import org.example.Products.Comment.Comment;

import java.util.List;

public class HibernateProductQuantityRepository implements ProductQuantityRepository {

    private final EntityManagerFactory entityManagerFactory;

    public HibernateProductQuantityRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<ProductQuantity> FindProductQuantities() {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            List<ProductQuantity> productQuantities = entityManager.createQuery("SELECT pq FROM ProductQuantity pq", ProductQuantity.class).getResultList();
            return productQuantities;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void SaveProductQuantity(ProductQuantity productQuantity) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(productQuantity);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            entityManager.getTransaction().getRollbackOnly();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void UpdateProductQuantity(ProductQuantity productQuantity, int quantity) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            productQuantity.setQuantity(quantity);
            entityManager.merge(productQuantity);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            entityManager.getTransaction().getRollbackOnly();
        } finally {
            entityManager.close();
        }
    }
}
