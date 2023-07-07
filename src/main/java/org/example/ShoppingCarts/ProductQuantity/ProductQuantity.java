package org.example.ShoppingCarts.ProductQuantity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.example.Products.Product;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class ProductQuantity implements Serializable {

    @Id
    private String id;

    @OneToOne
    private Product product;

    private int quantity;

    public ProductQuantity(String id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public ProductQuantity() {

    }

    public double getPrice() {
        return quantity * product.getPrice();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductQuantity that = (ProductQuantity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
