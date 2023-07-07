package org.example.ShoppingCarts.Sales;

import jakarta.persistence.*;
import org.example.ShoppingCarts.ProductQuantity.ProductQuantity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Sale implements Serializable {

    @Id
    public String id;

    @Column(name = "_user")
    public String user;

    @OneToMany(fetch = FetchType.EAGER)
    public List<ProductQuantity> productQuantities;

    public double totalPrice;

    public String date;

    public Sale(String user, List<ProductQuantity> productQuantities, double totalPrice, String date) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.productQuantities = productQuantities;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public Sale() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<ProductQuantity> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(List<ProductQuantity> productQuantities) {
        this.productQuantities = productQuantities;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
