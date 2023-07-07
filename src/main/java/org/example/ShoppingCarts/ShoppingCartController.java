package org.example.ShoppingCarts;

import org.example.Main;
import org.example.Products.Product;
import org.example.Products.ProductRepository;
import org.example.ShoppingCarts.ProductQuantity.ProductsSold;
import org.example.ShoppingCarts.Sales.Sale;
import org.example.ShoppingCarts.Sales.SalesRepository;
import org.example.ShoppingCarts.ProductQuantity.ProductQuantity;
import org.example.ShoppingCarts.ProductQuantity.ProductQuantityRepository;
import io.javalin.http.Context;
import org.example.Users.User;
import org.example.Users.UserRepository;

import java.io.IOException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ShoppingCartController {

    private final ShoppingCartRepository shoppingCartRepository;

    private final ProductQuantityRepository productQuantityRepository;

    private final ProductRepository productRepository;

    private final SalesRepository salesRepository;

    public ShoppingCartController(ShoppingCartRepository shoppingCartRepository, ProductQuantityRepository productQuantityRepository, ProductRepository productRepository, SalesRepository salesRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productQuantityRepository = productQuantityRepository;
        this.productRepository = productRepository;
        this.salesRepository = salesRepository;
    }

    public ShoppingCart FindShoppingCartByUser(String user) {
        var shoppingCarts = shoppingCartRepository.FindShoppingCarts();
        for (ShoppingCart shoppingCart : shoppingCarts) {
            if (shoppingCart.getUser().equals(user)) {
                return shoppingCart;
            }
        }
        return null;
    }

    public ProductQuantity FindProductQuantityById(String id) {
        var productQuantities = productQuantityRepository.FindProductQuantities();
        for (ProductQuantity productQuantity : productQuantities) {
            if (productQuantity.getId().equals(id)) {
                return productQuantity;
            }
        }
        return null;
    }

    public ProductQuantity FindProductQuantityByProduct(ShoppingCart shoppingCart, String product) {
        var productQuantities = shoppingCart.getProductQuantities();
        for (ProductQuantity productQuantity : productQuantities) {
            if (productQuantity.getProduct().getId().equals(product)) {
                return productQuantity;
            }
        }
        return null;
    }

    public boolean ProductIsInShoppingCart(ShoppingCart shoppingCart, String product) {
        var productQuantities = shoppingCart.getProductQuantities();
        for (ProductQuantity productQuantity : productQuantities) {
            if (productQuantity.getProduct().getId().equals(product)) {
                return true;
            }
        }
        return false;
    }


    public void ShowShoppingCartView(Context ctx) {
        Map<String, Object> data = new HashMap<String, Object>();
        String sessionId = ctx.req().getSession().getId();
        ShoppingCart shoppingCart = null;
        if (FindShoppingCartByUser(sessionId) == null && ctx.sessionAttribute("user") == null) {
            shoppingCartRepository.SaveShoppingCart(new ShoppingCart(sessionId, null));
            shoppingCart = FindShoppingCartByUser(sessionId);
        } else if (ctx.sessionAttribute("user") != null) {
            User user = ctx.sessionAttribute("user");
            if (FindShoppingCartByUser(user.getUsername()) == null) {
                if (FindShoppingCartByUser(sessionId) == null) {
                    shoppingCartRepository.SaveShoppingCart(new ShoppingCart(user.getUsername(), null));
                } else {
                    shoppingCart = FindShoppingCartByUser(sessionId);
                    shoppingCartRepository.UpdateShoppingCart(shoppingCart, user.getUsername());
                }
            }
            shoppingCart = FindShoppingCartByUser(user.getUsername());
            String username = user.getUsername();
            data.put("user", username);
        } else {
            shoppingCart = FindShoppingCartByUser(sessionId);
        }
        double totalPrice = 0;
        var productQuantities = shoppingCart.getProductQuantities();
        for (ProductQuantity productQuantity : productQuantities) {
            totalPrice = totalPrice + productQuantity.getPrice();
        }
        data.put("totalPrice", totalPrice);
        data.put("products", shoppingCart.getProductQuantities());
        data.put("amount", ctx.sessionAttribute("amount"));
        ctx.render("views/ShoppingCart.ftlh", data);
    }

    public void AddShoppingCartProduct(Context ctx) {
        String sessionId = ctx.req().getSession().getId();
        ShoppingCart shoppingCart = null;
        if (FindShoppingCartByUser(sessionId) == null && ctx.sessionAttribute("user") == null) {
            shoppingCartRepository.SaveShoppingCart(new ShoppingCart(sessionId, null));
            shoppingCart = FindShoppingCartByUser(sessionId);
        } else if (ctx.sessionAttribute("user") != null) {
            User user = ctx.sessionAttribute("user");
            if (FindShoppingCartByUser(user.getUsername()) == null) {
                if (FindShoppingCartByUser(sessionId) == null) {
                    shoppingCartRepository.SaveShoppingCart(new ShoppingCart(user.getUsername(), null));
                } else {
                    shoppingCart = FindShoppingCartByUser(sessionId);
                    shoppingCartRepository.UpdateShoppingCart(shoppingCart, user.getUsername());
                }
            }
            shoppingCart = FindShoppingCartByUser(user.getUsername());
        } else {
            shoppingCart = FindShoppingCartByUser(sessionId);
        }
        var id = ctx.pathParam("id");
        var quantity = ctx.formParam("quantity");
        var products = productRepository.FindProducts();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                if (ProductIsInShoppingCart(shoppingCart, product.getId())) {
                    ProductQuantity productQuantity = FindProductQuantityByProduct(shoppingCart, id);
                    productQuantityRepository.UpdateProductQuantity(productQuantity, Integer.valueOf(quantity) + productQuantity.getQuantity());
                } else {
                    String randomUUID = UUID.randomUUID().toString();
                    productQuantityRepository.SaveProductQuantity(new ProductQuantity(randomUUID, product, Integer.valueOf(quantity)));
                    shoppingCart.getProductQuantities().add(FindProductQuantityById(randomUUID));
                }
            }
        }
        ctx.sessionAttribute("amount", shoppingCart.products());
        ctx.redirect("/products");
    }

    public void RemoveProduct(Context ctx) {
        String sessionId = ctx.req().getSession().getId();
        ShoppingCart shoppingCart = null;
        if (ctx.sessionAttribute("user") != null) {
            User user = ctx.sessionAttribute("user");
            shoppingCart = FindShoppingCartByUser(user.getUsername());
        } else {
            shoppingCart = FindShoppingCartByUser(sessionId);
        }
        var id = ctx.pathParam("id");
        var productQuantities = productQuantityRepository.FindProductQuantities();
        for (ProductQuantity productQuantity : productQuantities) {
            if (productQuantity.getId().equals(id)) {
                shoppingCart.getProductQuantities().remove(productQuantity);
            }
        }
        ctx.sessionAttribute("amount", shoppingCart.products());
        ctx.redirect("/shopping-cart");
    }

    public void Purchase(Context ctx) throws CloneNotSupportedException, IOException {
        double totalPrice = Double.parseDouble(ctx.pathParam("totalPrice"));
        User user = ctx.sessionAttribute("user");
        if (totalPrice != 0) {
            if (user != null) {
                var shoppingCarts = shoppingCartRepository.FindShoppingCarts();
                for (ShoppingCart shoppingCart : shoppingCarts) {
                    if (shoppingCart.getUser() == user.getUsername()) {
                        List getProductsInArray = new ArrayList<>();
                        var products = shoppingCart.getProductQuantities();
                        for (ProductQuantity product : products) {
                            getProductsInArray.add(product);
                        }
                        Sale sale = new Sale(user.getUsername(), getProductsInArray, totalPrice, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        salesRepository.SaveSale(sale);
                        ProductsSold.ProductsSold(productQuantityRepository, true);
                        shoppingCart.getProductQuantities().clear();
                        ctx.sessionAttribute("amount", 0);
                    }
                    ctx.redirect("/shopping-cart");
                }
            } else {
                ctx.redirect("/register");
            }
        } else {
            ctx.redirect("/shopping-cart");
        }
    }

    public void Sales(Context ctx) {
        Map<String, Object> data = new HashMap<String, Object>();
        User user = ctx.sessionAttribute("user");
        String username = user.getUsername();
        data.put("user", username);
        data.put("amount", ctx.sessionAttribute("amount"));
        var sales = salesRepository.FindSales();
        data.put("sales", sales);
        data.put("amount", ctx.sessionAttribute("amount"));
        ctx.render("views/Sales.ftlh", data);
    }

    public void Dashboard(Context ctx) {
        Map<String, Object> data = new HashMap<>();
        User user = ctx.sessionAttribute("user");
        String username = user.getUsername();
        data.put("user", username);
        data.put("amount", ctx.sessionAttribute("amount"));
        ctx.render("views/Dashboard.ftlh", data);
    }
}