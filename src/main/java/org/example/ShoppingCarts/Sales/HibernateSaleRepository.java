package org.example.ShoppingCarts.Sales;

import jakarta.persistence.EntityManagerFactory;
import org.example.Products.Product;
import org.example.Services.CockroachServices;
import org.example.ShoppingCarts.ProductQuantity.ProductQuantity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HibernateSaleRepository implements SalesRepository {

    private final EntityManagerFactory entityManagerFactory;

    public HibernateSaleRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Sale> FindSales() {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            List<Sale> sales = entityManager.createQuery("SELECT s FROM Sale s", Sale.class).getResultList();
            return sales;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void SaveSale(Sale sale) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(sale);
            entityManager.getTransaction().commit();
            // Saving in CockroachDB
            CockroachServices.getInstance().saveSale(sale);
        } catch (Exception exception) {
            entityManager.getTransaction().getRollbackOnly();
        } finally {
            entityManager.close();
        }
    }
}
