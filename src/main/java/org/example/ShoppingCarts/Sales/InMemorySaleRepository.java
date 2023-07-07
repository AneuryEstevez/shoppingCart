package org.example.ShoppingCarts.Sales;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemorySaleRepository implements SalesRepository {

    private final List<Sale> sales;

    public InMemorySaleRepository() {
        this.sales = new CopyOnWriteArrayList<>();
    }

    @Override
    public List<Sale> FindSales() {
        return sales;
    }

    @Override
    public void SaveSale(Sale sale) {
        sales.add(sale);
    }
}
