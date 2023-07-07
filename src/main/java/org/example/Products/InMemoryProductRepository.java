package org.example.Products;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryProductRepository implements  ProductRepository {

    private final List<Product> products;

    public InMemoryProductRepository() {
        this.products = new CopyOnWriteArrayList<>();
        InitialProducts();
    }

    public void InitialProducts() {
        SaveProduct(new Product("Fancy Feast",
                "Food",
                18.99,
                "Variety pack of gourmet cat food keeps finicky eaters interested and eating with palatable flavors.\n" +
                "Features grilled cuts of seafood that are grilled to perfection in a rich gravy.\n" +
                "Serve alone or mix with dry food for a delectable texture—it’s a great way to add moisture to your cat’s diet.\n" +
                "Made in the USA with vitamins and minerals, delivering a 100% complete and balanced nutrition.\n" +
                "Offers your feline a flaky, delicate texture he won't be able to resist.\n" +
                "\n" +
                "Make dinner a black tie optional affair with the Fancy Feast Grilled Seafood Feast Variety Pack. With a delicious combo of your cat’s favorite flavors, this gourmet food features tender cuts of seafood that are grilled to purr-fection and basted in a savory gravy. The added vitamins and minerals offer complete and balanced nutrition, making every mealtime a nutritious and delicious occasion. And since variety is the spice of life, the selection of flavors will keep him interested and happy, with an even mix of Grilled Tuna Feast in Gravy, Grilled Salmon Feast in Gravy and Grilled Seafood Feast in Gravy."));
        SaveProduct(new Product("Frisco", "Litter & Litter Boxes", 20.00, "High Sided Cat Litter Box, Extra Large 24-in"));
        SaveProduct(new Product("Temptations", "Treats", 8.48, "Classic Tasty Chicken Soft & Crunchy Cat Treats"));
        SaveProduct(new Product("Frisco", "Bowls & Feeders", 12.44, "Double-Sided Ceramic Elevated Cat Bowl"));
        SaveProduct(new Product("Safe Cat", "Leashes, Collars & ID Tags", 6.48, "Snag-Proof Polyester Breakaway Cat Collar with Bell"));
        SaveProduct(new Product("SmartyKat", "Toys", 12.11, "Hot Pursuit Electronic Concealed Motion Cat Toy, Blue"));
        SaveProduct(new Product("Safari", "Grooming Supplies", 15.99, "Self-Cleaning Slicker Brush for Cats"));
        SaveProduct(new Product("Pets First", "Clothing & Accessories", 13.99, "NFL Cat T-Shirt"));
    }

    @Override
    public List<Product> FindProducts() {
        return products;
    }

    @Override
    public void SaveProduct(Product product) {
        products.add(product);
    }

    @Override
    public void UpdateProduct(Product product, String name, String category,  String description, Double price) {
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setDescription(description);
    }

    @Override
    public void DeleteProduct(Product product) {
        products.remove(product);
    }
}
