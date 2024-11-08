package mk.ukim.finki.wpaud.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wpaud.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Category> categories = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public static List<Manufacturer> manufacturers = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();
    public static List<ShoppingCart> shoppingCarts = new ArrayList<>();

    @PostConstruct
    public void init() {
        categories.add(new Category("Software", "Software category"));
        categories.add(new Category("Books", "Books category"));

        users.add(new User("pero.pero", "pp", "Pero", "Perovski"));
        users.add(new User("riste.stojanov", "rs", "Riste", "Stojanov"));

        Manufacturer manufacturer = new Manufacturer("Nike", "NY NY");
        manufacturers.add(manufacturer);

        Category category = new Category("Sport", "SportCategory");
        categories.add(category);

        products.add(new Product("Ball 1", 235.8, 7, category, manufacturer));
        products.add(new Product("Ball 2", 235.8, 7, category, manufacturer));
        products.add(new Product("Ball 3", 235.8, 7, category, manufacturer));
    }

}
