package org.example.ShoppingCarts;

import org.example.ShoppingCarts.ProductQuantity.ProductQuantity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ShoppingCart implements Serializable {

    private String id;

    private String userId;

    private List<ProductQuantity> productQuantities;

    public ShoppingCart(String userId, List<ProductQuantity> productQuantities) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.productQuantities = new ArrayList<>();
    }

    public int products() {
        return productQuantities.size();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return userId;
    }

    public void setUser(String userId) {
        this.userId = userId;
    }

    public List<ProductQuantity> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(List<ProductQuantity> productQuantities) {
        this.productQuantities = productQuantities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
