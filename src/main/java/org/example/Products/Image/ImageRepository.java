package org.example.Products.Image;


import java.util.List;

public interface ImageRepository {

    List<Image> FindImages();

    void SaveImage(Image image);

    void DeleteImage(Image image);
}
