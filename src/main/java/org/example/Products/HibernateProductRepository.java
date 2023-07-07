package org.example.Products;

import jakarta.persistence.EntityManagerFactory;
import org.example.Products.Image.Image;
import org.example.Products.Image.ImageRepository;
import org.example.ShoppingCarts.ProductQuantity.ProductQuantity;
import org.example.ShoppingCarts.Sales.Sale;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class HibernateProductRepository implements ProductRepository {

    private final EntityManagerFactory entityManagerFactory;

    private final ImageRepository imageRepository;

    public HibernateProductRepository(EntityManagerFactory entityManagerFactory, ImageRepository imageRepository) {
        this.entityManagerFactory = entityManagerFactory;
        this.imageRepository = imageRepository;
        InitialProducts();
    }

    public Image UrlToImage(String urlImage) {
        try {
            URL url = new URL(urlImage);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            String encodedString = Base64.getEncoder().encodeToString(bytes);
            Image img = new Image(encodedString);
            imageRepository.SaveImage(img);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void InitialProducts() {
        Product product = (new Product("Fancy Feast",
                "Food",
                18.99,
                "Variety pack of gourmet cat food keeps finicky eaters interested and eating with palatable flavors.\n" +
                        "Features grilled cuts of seafood that are grilled to perfection in a rich gravy.\n" +
                        "Serve alone or mix with dry food for a delectable texture—it’s a great way to add moisture to your cat’s diet.\n" +
                        "Made in the USA with vitamins and minerals, delivering a 100% complete and balanced nutrition.\n" +
                        "Offers your feline a flaky, delicate texture he won't be able to resist.\n" +
                        "\n" +
                        "Make dinner a black tie optional affair with the Fancy Feast Grilled Seafood Feast Variety Pack. " +
                        "With a delicious combo of your cat’s favorite flavors, this gourmet food features tender cuts of seafood that are grilled to purr-fection and basted in a savory gravy. " +
                        "The added vitamins and minerals offer complete and balanced nutrition, making every mealtime a nutritious and delicious occasion. " +
                        "And since variety is the spice of life, the selection of flavors will keep him interested and happy, with an even mix of Grilled Tuna Feast in Gravy, Grilled Salmon Feast in Gravy and Grilled Seafood Feast in Gravy."));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/76096_MAIN._AC_SL1200_V1667518942_.jpg"));
        SaveProduct(product);
        product = (new Product("Friskies",
                "Food",
                25.88,
                "Seafood Sensations Dry Cat Food"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/142565_MAIN._AC_SL1200_V1675967355_.jpg"));
        SaveProduct(product);
        product = (new Product("Cat Chow",
                "Food",
                23.99,
                "Purina Naturals Original with Added Vitamins, Minerals & Nutrients Dry Cat Food"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/211440_MAIN._AC_SL1200_V1675089256_.jpg"));
        SaveProduct(product);

        product = (new Product("Frisco",
                "Litter & Litter Boxes",
                20.00,
                "High Sided Cat Litter Box, Extra Large 24-in"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/70450_MAIN._AC_SL1200_V1565284571_.jpg"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/70450_PT1._AC_SL1200_V1565284391_.jpg"));
        SaveProduct(product);
        product = (new Product("Van Ness",
                "Litter & Litter Boxes",
                15.44,
                "Enclosed Cat Litter Pan"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/141371_MAIN._AC_SL1200_V1675279930_.jpg"));
        SaveProduct(product);
        product = (new Product("Frisco",
                "Litter & Litter Boxes",
                20.00,
                "Bundle: Plastic Litter Scooper with Caddy + IRIS Open Top Litter Box with Shield"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/298166_MAIN._AC_SL1200_V1623198146_.jpg"));
        SaveProduct(product);

        product = (new Product("Temptations",
                "Treats",
                8.48,
                "Classic Tasty Chicken Soft & Crunchy Cat Treats"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/81328_MAIN._AC_SL1200_V1568657909_.jpg"));
        SaveProduct(product);
        product = (new Product("Greenies",
                "Treats",
                4.98,
                "Feline Oven Roasted Chicken Flavor Adult Dental Cat Treats"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/214729_MAIN._AC_SL1200_V1577112184_.jpg"));
        SaveProduct(product);
        product = (new Product("Sheba",
                "Treats",
                1.98,
                "Meaty Tender Sticks Chicken Cat Treats"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/109828_MAIN._AC_SL1200_V1675959794_.jpg"));
        SaveProduct(product);

        product = (new Product("Frisco",
                "Bowls & Feeders",
                12.44,
                "Double-Sided Ceramic Elevated Cat Bowl"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/232081_PT1._AC_SL1200_V1605109355_.jpg"));
        SaveProduct(product);
        product = (new Product("Petmate",
                "Bowls & Feeders",
                0.98,
                "Kitty Kap Can Cover, Color Varies"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/catalog/68546._AC_SL1200_V1460478784_.jpg"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/68546_PT2._AC_SL1200_V1489184250_.jpg"));
        SaveProduct(product);
        product = (new Product("Catit",
                "Bowls & Feeders",
                27.99,
                "Flower Plastic Cat Fountain, 100-oz"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/151262_MAIN._AC_SL1200_V1628020050_.jpg"));
        SaveProduct(product);
        product = (new Product("Petstages",
                "Bowls & Feeders",
                8.94,
                "Kitty Slow Feeder Cat Bowl"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/227086_MAIN._AC_SL1200_V1636515152_.jpg"));
        SaveProduct(product);
        product = (new Product("Signature Housewares",
                "Bowls & Feeders",
                5.99,
                "Coastal Fish Non-Skid Ceramic Cat Bowl"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/103441_MAIN._AC_SL1200_V1517251654_.jpg"));
        SaveProduct(product);

        product = (new Product("Frisco ",
                "Leashes, Collars & ID Tags",
                9.36,
                "Nylon Breakaway Cat Collar with Bell"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/153148_MAIN._AC_SL1200_V1571280568_.jpg"));
        SaveProduct(product);
        product = (new Product("GoTags",
                "Leashes, Collars & ID Tags",
                13.95,
                "Nylon Personalized Reflective Breakaway Cat Collar with Bell"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/152990_MAIN._AC_SL1200_V1542994023_.jpg"));
        SaveProduct(product);
        product = (new Product("PetSafe",
                "Leashes, Collars & ID Tags",
                16.95,
                "Come With Me Kitty Nylon Cat Harness & Bungee Leash"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/67153_MAIN._AC_SL1200_V1671742681_.jpg"));
        SaveProduct(product);

        product = (new Product("Frisco",
                "Toys",
                10.35,
                "Butterfly Cat Tracks Cat Toy"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/161805_MAIN._AC_SL1200_V1565736429_.jpg"));
        SaveProduct(product);
        product = (new Product("SmartyKat",
                "Toys",
                12.11,
                "Hot Pursuit Electronic Concealed Motion Cat Toy, Blue"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/132636_MAIN._AC_SL1200_V1535648593_.jpg"));
        SaveProduct(product);
        product = (new Product("Frisco",
                "Toys",
                5.23,
                "Bird Teaser with Feathers Cat Toy"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/161199_MAIN._AC_SL1200_V1568240233_.jpg"));
        SaveProduct(product);
        product = (new Product("Pet Fit For Life",
                "Toys",
                9.95,
                "5 Piece Squiggly Worm Wand Cat Toy"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/140078_MAIN._AC_SL1200_V1589320918_.jpg"));
        SaveProduct(product);

        product = (new Product("Safari",
                "Grooming Supplies",
                15.99,
                "Self-Cleaning Slicker Brush for Cats"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/61234_MAIN._AC_SL1200_V1673648562_.jpg"));
        SaveProduct(product);
        product = (new Product("Frisco",
                "Grooming Supplies",
                5.89,
                "Cat Nail Clippers"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/206880_MAIN._AC_SL1200_V1590671477_.jpg"));
        SaveProduct(product);

        product = (new Product("Pets First",
                "Clothing & Accessories",
                13.99,
                "NFL Cat T-Shirt"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/177576_MAIN._AC_SL1200_V1572446958_.jpg"));
        SaveProduct(product);
        product = (new Product("Frisco",
                "Clothing & Accessories",
                5.03,
                "Shark Cat Costume, One Size"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/261100_MAIN._AC_SL1200_V1659623787_.jpg"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/261100_PT7._AC_SL1200_V1624307906_.jpg"));
        SaveProduct(product);
        product = (new Product("Catit",
                "Clothing & Accessories",
                5.93,
                "Sense 2.0 Self Groomer Cat Brush"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/191544_MAIN._AC_SL1200_V1565298443_.jpg"));
        SaveProduct(product);
        product = (new Product("Mr. Peanut's",
                "Clothing & Accessories",
                19.99,
                "Hand Gloves Dog & Cat Grooming & Deshedding Aid, 2 count"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/151436_MAIN._AC_SL1200_V1535120012_.jpg"));
        SaveProduct(product);
        product = (new Product("JW Pet",
                "Clothing & Accessories",
                6.40,
                "Gripsoft Cat Slicker Brush"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/55073_MAIN._AC_SL1200_V1497469815_.jpg"));
        SaveProduct(product);
        product = (new Product("Top Performance",
                "Clothing & Accessories",
                18.23,
                "Cat Grooming Bag"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/124779_MAIN._AC_SL1200_V1495546555_.jpg"));
        SaveProduct(product);
        product = (new Product("Burt's Bees",
                "Clothing & Accessories",
                7.48,
                "Dander Reducing Wipes with Colloidal Oat Flour & Aloe Vera for Cats, 50 count"));
        product.getImages().add(UrlToImage("https://www.chewy.com/burts-bees-dander-reducing-wipes/dp/121654"));
        SaveProduct(product);
        product = (new Product("Purrdy Paws",
                "Clothing & Accessories",
                7.99,
                "Soft Cat Nail Caps"));
        product.getImages().add(UrlToImage("https://image.chewy.com/is/image/catalog/126833_MAIN._AC_SL1200_V1573061280_.jpg"));
        SaveProduct(product);
    }

    @Override
    public List<Product> FindProducts() {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            List<Product> products = entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
            return products;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void SaveProduct(Product product) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(product);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            entityManager.getTransaction().getRollbackOnly();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void UpdateProduct(Product product, String name, String category, String description, Double price) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            product.setName(name);
            product.setCategory(category);
            product.setDescription(description);
            product.setPrice(price);
            entityManager.merge(product);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            entityManager.getTransaction().getRollbackOnly();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void DeleteProduct(Product product) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(product);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            entityManager.getTransaction().getRollbackOnly();
        } finally {
            entityManager.close();
        }
    }
}
