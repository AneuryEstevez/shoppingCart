package org.example;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.JavalinRenderer;
import io.javalin.rendering.template.JavalinFreemarker;
import io.javalin.websocket.WsContext;
import jakarta.persistence.Persistence;
import org.example.Products.Comment.HibernateCommentRepository;
import org.example.Products.HibernateProductRepository;
import org.example.Products.Image.HibernateImageRepository;
import org.example.Products.ProductController;
import org.example.Services.CockroachServices;
import org.example.ShoppingCarts.InMemoryShoppingCartRepository;
import org.example.ShoppingCarts.ProductQuantity.HibernateProductQuantityRepository;
import org.example.ShoppingCarts.ProductQuantity.ProductsSold;
import org.example.ShoppingCarts.Sales.HibernateSaleRepository;
import org.example.ShoppingCarts.ShoppingCartController;
import org.example.Users.*;
import io.javalin.security.AccessManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    private static List<WsContext> connectedUsers = new CopyOnWriteArrayList<>();
    private static List<WsContext> loggedUsers = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws SQLException {

        var properties = new HashMap<>();
        var entityManagerFactory = Persistence.createEntityManagerFactory("H2PersistenceUnit", properties);
        UserRepository userRepository = new HibernateUserRepository(entityManagerFactory);
        AccessManager accessManager = new InSessionAccessManager(userRepository);

        var app = Javalin.create(javalinConfig -> {
            javalinConfig.staticFiles.add("/static");
            javalinConfig.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/views";
                staticFileConfig.directory = "/views";
                staticFileConfig.location = Location.CLASSPATH;
            });
            javalinConfig.accessManager(accessManager);
        });

        var configuration = JavalinFreemarker.Companion.defaultFreemarkerEngine();
        JavalinRenderer.register(new JavalinFreemarker(configuration), ".ftlh");

       var userController = new UserController(userRepository);

        app.before("/", ctx -> {
            ctx.sessionAttribute("amount", 0);
            ctx.redirect("/cookie-login");
        });

        var imageRepository = new HibernateImageRepository(entityManagerFactory);
        var productRepository =  new HibernateProductRepository(entityManagerFactory, imageRepository);
        var commentRepository = new HibernateCommentRepository(entityManagerFactory);
        var productController = new ProductController(productRepository, commentRepository, imageRepository);

        var productQuantityRepository = new HibernateProductQuantityRepository(entityManagerFactory);
        var shoppingCartRepository = new InMemoryShoppingCartRepository();
        var salesRepository = new HibernateSaleRepository(entityManagerFactory);

        var shoppingCartController = new ShoppingCartController(shoppingCartRepository, productQuantityRepository, productRepository, salesRepository);

        // Cockroach Services
        CockroachServices c = CockroachServices.getInstance();
        c.getConnection();
        c.createTable();

        // CRUD Product

        app.get("/manage-products", productController::ShowManageProductsView, Role.ADMIN);

        app.get("/create-product", productController::ShowCreateProductView, Role.ADMIN);
        app.post("/create-product", productController::CreateProduct, Role.ADMIN);

        app.get("/update-product/{id}", productController::ShowUpdateProductView, Role.ADMIN);
        app.post("/update-product/{id}", productController::UpdateProduct, Role.ADMIN);

        app.get("/delete-product/{id}", productController::DeleteProduct, Role.ADMIN);

        // ShoppingCart, ProductAmount & Comments

        app.get("/products", productController::ShowProductsView);

        app.get("/product/{id}", productController::ShowProductView);

        app.post("/products/{id}/{user}", productController::UploadComment);

        app.get("/product/{idProduct}/{idComment}", productController::DeleteComment);

        app.get("/shopping-cart", shoppingCartController::ShowShoppingCartView);
        app.post("/products/{id}", shoppingCartController::AddShoppingCartProduct);

        app.get("/shopping-cart/{id}", shoppingCartController::RemoveProduct);

        app.get("purchase/{totalPrice}", shoppingCartController::Purchase);

        app.get("/sales", shoppingCartController::Sales, Role.ADMIN);

        app.get("/dashboard", shoppingCartController::Dashboard, Role.ADMIN);

        // User

        app.get("cookie-login", userController::CookieLogin);

        app.get("/login", userController::ShowLoginView);
        app.post("/login", userController::ValidateUser);

        app.get("/register", userController::ShowRegisterView);
        app.post("/register", userController::CreateUser);

        app.get("/login/logout", userController::Logout);

        app.ws("/shoppingCart", ws -> {
            ws.onConnect(ctx -> {
                connectedUsers.add(ctx);
//                System.out.println("Online users: " + connectedUsers.size());
                broadcastMessage(Map.of(
                        "type", "cantidadUsuarios",
                        "cantidad", loggedUsers.size()
                        ));
            });

            ws.onMessage(ctx -> {
                System.out.println(ctx.message());
                Map<String, Object> mensaje = ctx.messageAsClass(Map.class);
                System.out.println(mensaje.get("type"));
                if (mensaje.get("type").equals("InitProductsSold")) {
                    ctx.send(ProductsSold.ProductsSold(productQuantityRepository, false));
                } else if (mensaje.get("type").equals("deleteComment")) {
                    System.out.println(mensaje.get("idComment"));
                } else if (mensaje.get("type").equals("usuarioLogueado") ) {
                    System.out.println("Usuario logueado");
                    loggedUsers.add(ctx);
                    broadcastMessage(Map.of(
                            "type", "cantidadUsuarios",
                            "cantidad", loggedUsers.size()
                    ));
                }
            });

            ws.onClose(ctx -> {
                connectedUsers.remove(ctx);
                loggedUsers.remove(ctx);
                System.out.println("Online users: " + connectedUsers.size());
                broadcastMessage(Map.of(
                        "type", "cantidadUsuarios",
                        "cantidad", connectedUsers.size()
                        ));
            });
        });

        app.start(7070);
    }

    public static void broadcastMessage(Map<String, Object> mensage) throws IOException {
        connectedUsers.stream().filter(ctx -> ctx.session.isOpen()).forEach(ctx -> {
            ctx.send(mensage);
        });
    };
}