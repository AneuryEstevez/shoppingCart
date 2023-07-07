package org.example.Products;

import jakarta.persistence.*;
import org.example.Products.Image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Product implements Serializable {

    @Id
    private String id;

    private String name;

    private String category;

    private Double price;

    @Column(length = 2048)
    private String description;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Image> images;

    public Product(String name, String category, Double price, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.images = new ArrayList<>();
    }

    public Product() {

    }

    public int imagesSize() {
        return images.size();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String name) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
