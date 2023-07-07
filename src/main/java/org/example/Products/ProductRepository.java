package org.example.Products;

import java.util.List;

public interface ProductRepository {

    List<Product> FindProducts();

    void SaveProduct(Product product);

    void UpdateProduct(Product product, String name, String category, String description, Double price);

    void DeleteProduct(Product product);
}
