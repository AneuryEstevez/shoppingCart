package org.example.ShoppingCarts;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryShoppingCartRepository implements ShoppingCartRepository {

    private final List<ShoppingCart> shoppingCarts;

    public InMemoryShoppingCartRepository() {
        this.shoppingCarts = new CopyOnWriteArrayList<>();
    }

    @Override
    public List<ShoppingCart> FindShoppingCarts() {
        return shoppingCarts;
    }

    @Override
    public void SaveShoppingCart(ShoppingCart shoppingCart) {
        shoppingCarts.add(shoppingCart);
    }

    @Override
    public void UpdateShoppingCart(ShoppingCart shoppingCart, String name) {
        shoppingCart.setUser(name);
    }

    @Override
    public void DeleteShoppingCart(ShoppingCart shoppingCart) {
        shoppingCarts.remove(shoppingCart);
    }
}
