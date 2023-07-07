package org.example.Services;


import org.example.ShoppingCarts.Sales.Sale;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CockroachServices {
    private static CockroachServices instance;

    public CockroachServices() {
    }

    public static CockroachServices getInstance() {
        if (instance == null) {
            instance = new CockroachServices();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        String url = System.getenv("JDBC_DATABASE_URL");
        if (url != null) {
            ds.setUrl(url);
        } else {
            ds.setUrl("jdbc:postgresql://crown-shadow-9342.7tt.cockroachlabs.cloud:26257/carritoCompras?sslmode=require&password=z-dQw9vCagVEKgnZdXp0eA&user=aneury");
        }
        return ds.getConnection();
    }


    public void createTable() throws SQLException {
        String TABLE_NAME = "sales";
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME)
                .append("(id uuid PRIMARY KEY, ")
                .append("username string,")
                .append("productQuantity int,")
                .append("totalPrice float,")
                .append("date string);");

        String query = sb.toString();
        Statement stmt = getConnection().createStatement();
        stmt.execute(query);
    }

    public void saveSale(Sale sale) throws SQLException {
        StringBuilder sb = new StringBuilder("INSERT INTO sales")
                .append("(id, username, productQuantity, totalPrice, date) ")
                .append("VALUES (?,?,?,?,?)");

        String query = sb.toString();
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, sale.getId().toString());
        preparedStatement.setString(2, sale.getUser());
        preparedStatement.setInt(3, sale.getProductQuantities().size());
        preparedStatement.setFloat(4, (float) sale.getTotalPrice());
        preparedStatement.setString(5, sale.getDate());
        preparedStatement.execute();
    }
}
