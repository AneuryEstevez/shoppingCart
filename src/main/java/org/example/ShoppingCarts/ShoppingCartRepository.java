package org.example.ShoppingCarts;

import java.util.List;

public interface ShoppingCartRepository {

    List<ShoppingCart> FindShoppingCarts();

    void SaveShoppingCart(ShoppingCart shoppingCart);

    void UpdateShoppingCart(ShoppingCart shoppingCart, String name);

    void DeleteShoppingCart(ShoppingCart shoppingCart);
}
