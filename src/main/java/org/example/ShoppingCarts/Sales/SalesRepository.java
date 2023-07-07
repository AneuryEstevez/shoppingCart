package org.example.ShoppingCarts.Sales;

import java.util.List;

public interface SalesRepository {

    List<Sale> FindSales();

    void SaveSale(Sale sale);
}
