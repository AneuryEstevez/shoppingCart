package org.example.ShoppingCarts.ProductQuantity;

import org.example.Products.Product;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryProductQuantityRepository implements ProductQuantityRepository {

    private final List<ProductQuantity> productQuantities;
    public InMemoryProductQuantityRepository() {
        this.productQuantities = new CopyOnWriteArrayList<>();
    }
    @Override
    public List<ProductQuantity> FindProductQuantities() {
        return productQuantities;
    }

    @Override
    public void SaveProductQuantity(ProductQuantity productQuantity) {
        productQuantities.add(productQuantity);
    }

    @Override
    public void UpdateProductQuantity(ProductQuantity productQuantity, int quantity) {
        productQuantity.setQuantity(quantity);
    }
}
