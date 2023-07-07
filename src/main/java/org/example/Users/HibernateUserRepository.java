package org.example.Users;

import jakarta.persistence.EntityManagerFactory;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.util.List;

public class HibernateUserRepository implements UserRepository {

    private final EntityManagerFactory entityManagerFactory;

    public HibernateUserRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        InitialUsers();
    }

    public void InitialUsers() {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        User user = new User("admin", passwordEncryptor.encryptPassword("admin"));
        user.AddRole(Role.ADMIN);
        user.AddRole(Role.CUSTOMER);
        SaveUser(user);
    }

    @Override
    public List<User> FindUsers() {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
            return users;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void SaveUser(User user) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            entityManager.getTransaction().getRollbackOnly();
        } finally {
            entityManager.close();
        }
    }
}
