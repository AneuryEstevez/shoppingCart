package org.example.Products;

import io.javalin.http.Context;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import org.example.Main;
import org.example.Products.Comment.Comment;
import org.example.Products.Comment.CommentRepository;
import org.example.Products.Image.Image;
import org.example.Products.Image.ImageRepository;
import org.example.Users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.text.Element;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ProductController {

    private final ProductRepository productRepository;

    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;

    public ProductController(ProductRepository productRepository, CommentRepository commentRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.commentRepository = commentRepository;
        this.imageRepository = imageRepository;
    }

    public Product GetProductById(String id) {
        var products = productRepository.FindProducts();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return  product;
            }
        }
        return null;
    }

    public void ShowManageProductsView(Context ctx) {
        Map<String, Object> data = new HashMap<>();
        User user = ctx.sessionAttribute("user");
        String username = user.getUsername();
        data.put("user", username);
        var products = productRepository.FindProducts();
        data.put("products", products);
        data.put("amount", ctx.sessionAttribute("amount"));
        ctx.render("views/ManageProducts.ftlh", data);
    }

    public void ShowCreateProductView(Context ctx) {
        Map<String, Object> data = new HashMap<>();
        User user = ctx.sessionAttribute("user");
        String username = user.getUsername();
        data.put("user", username);
        data.put("amount", ctx.sessionAttribute("amount"));
        ctx.render("views/CreateProduct.ftlh", data);
    }

    public void CreateProduct(Context ctx) throws ServletException, IOException {
        var name = ctx.formParam("name");
        var category = ctx.formParam("category");
        var price = ctx.formParam("price");
        var description = ctx.formParam("description");
        Product product = new Product(name, category, Double.parseDouble(price), description);
        ctx.uploadedFiles("image").forEach(uploadedFile -> {
            try {
                byte[] bytes = uploadedFile.content().readAllBytes();
                String encodedString = Base64.getEncoder().encodeToString(bytes);
                Image img = new Image(encodedString);
                imageRepository.SaveImage(img);
                product.getImages().add(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        productRepository.SaveProduct(product);
        ctx.redirect("manage-products");
    }

    public void ShowUpdateProductView(Context ctx) {
        Map<String, Object> data = new HashMap<>();
        User user = ctx.sessionAttribute("user");
        String username = user.getUsername();
        data.put("user", username);
        var id = ctx.pathParam("id");
        data.put("product", GetProductById(id));
        data.put("amount", ctx.sessionAttribute("amount"));
        ctx.render("views/UpdateProduct.ftlh", data);
    }

    public void UpdateProduct(Context ctx) {
        var id = ctx.pathParam("id");
        var name = ctx.formParam("name");
        var category = ctx.formParam("category");
        var description = ctx.formParam("description");
        var price = ctx.formParam("price");
        productRepository.UpdateProduct(GetProductById(id), name, category, description, Double.parseDouble(price));
        ctx.redirect("/manage-products");
    }

    public void DeleteProduct(Context ctx) {
        var id = ctx.pathParam("id");
        productRepository.DeleteProduct(GetProductById(id));
        ctx.redirect("/manage-products");
    }

    public void ShowProductsView(Context ctx) {
        Map<String, Object> data = new HashMap<>();
        var products = productRepository.FindProducts();
        if (ctx.sessionAttribute("user") != null) {
            User user = ctx.sessionAttribute("user");
            String username = user.getUsername();
            data.put("user", username);
        }
        int currentPage = 1;
        String page = ctx.req().getParameter("page");
        if (page != null) {
            try {
                currentPage = Integer.parseInt(page);
            } catch (NumberFormatException e) {
                ctx.redirect("/products");
            }
        }
        int totalProducts = products.size();
        int pageSize = 8;
        int totalPages = (int) Math.ceil((double) totalProducts/pageSize);
        Pagination pagination = new Pagination(currentPage, totalPages, pageSize, totalProducts);
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalProducts);
        data.put("products", products.subList(startIndex, endIndex));
        data.put("pagination", pagination);
        data.put("amount", ctx.sessionAttribute("amount"));
        ctx.render("views/Products.ftlh", data);
    }

    public void ShowProductView(Context ctx) {
        Map<String, Object> data = new HashMap<>();
        if (ctx.sessionAttribute("user") != null) {
            User user = ctx.sessionAttribute("user");
            String username = user.getUsername();
            data.put("user", username);
        }
        var products = productRepository.FindProducts();
        data.put("products", products);
        data.put("amount", ctx.sessionAttribute("amount"));
        Product product = GetProductById(ctx.pathParam("id"));
        if (product != null) {
            data.put("mainProduct", product);
            var comments = commentRepository.FindComments();
            data.put("comments", comments);
        }
        ctx.render("views/Products.ftlh", data);
    }

    public void UploadComment(Context ctx) {
        var id = ctx.pathParam("id");
        var user = ctx.pathParam("user");
        var comment = ctx.formParam("comment");
        commentRepository.UploadComment(new Comment(id, user, comment, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        ctx.redirect("/product/" + id);
    }

    public Comment GetCommentById(String id) {
        var comments = commentRepository.FindComments();
        for (Comment comment : comments) {
            if (comment.getId().equals(id)) {
                return comment;
            }
        }
        return null;
    }

    public void DeleteComment(Context ctx) throws IOException {
        var idProduct = ctx.pathParam("idProduct");
        var idComment = ctx.pathParam("idComment");
        commentRepository.DeleteComment(GetCommentById(idComment));
        Main.broadcastMessage(Map.of(
                "type", "deleteComment",
                "idComment", idComment
        ));
        ctx.redirect("/product/" + idProduct);
    }
}
