package org.example.ShoppingCarts.ProductQuantity;

import java.util.List;

public interface ProductQuantityRepository {

    List<ProductQuantity> FindProductQuantities();

    void SaveProductQuantity(ProductQuantity productQuantity);

    void UpdateProductQuantity(ProductQuantity productQuantity, int quantity);
}
