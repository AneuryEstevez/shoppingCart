package org.example.Users;

import org.example.ShoppingCarts.ShoppingCart;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryUserRepository implements UserRepository {

    private final List<User> users;

    public  InMemoryUserRepository() {
        this.users = new CopyOnWriteArrayList<>();
        InitialUsers();
    }

    public void InitialUsers() {
        var admin = new User("admin", "admin");
        admin.AddRole(Role.ADMIN);
        admin.AddRole(Role.CUSTOMER);
        this.SaveUser(admin);
    }

    @Override
    public List<User> FindUsers() {
        return users;
    }

    @Override
    public void SaveUser(User user) {
        users.add(user);
    }
}
