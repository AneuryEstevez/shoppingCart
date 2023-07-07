package org.example.Products.Image;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Image implements Serializable {
    @Id
    private String id;
    @Lob
    private String src;

    public Image(String src) {
        this.id = UUID.randomUUID().toString();
        this.src = src;
    }

    public Image() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
