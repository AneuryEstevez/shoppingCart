package org.example.ShoppingCarts.ProductQuantity;

import org.example.Main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ProductsSold {

    public static Map<String, Object> ProductsSold(ProductQuantityRepository productQuantityRepository, Boolean broadcast) throws IOException {
        Map<String, Object> data = new HashMap<>();
        var productQuantities = productQuantityRepository.FindProductQuantities();
        for (ProductQuantity productQuantity : productQuantities) {
            String product = productQuantity.getProduct().getName();
            int quantity = productQuantity.getQuantity();

            if (data.containsKey(product)) {
                quantity = quantity + productQuantity.getQuantity();
            }

            data.put(product, quantity);
        }

        Map<String, Object> datos = Map.of(
                "type", "getProductsSold",
                "products", data
        );

        if (!broadcast) {
            return datos;
        }

        Main.broadcastMessage(datos);
        return null;
    }
}
