package mk.ukim.finki.wpaud.selenium;

import mk.ukim.finki.wpaud.model.Category;
import mk.ukim.finki.wpaud.model.User;
import mk.ukim.finki.wpaud.model.Manufacturer;
import mk.ukim.finki.wpaud.model.enumerations.Role;
import mk.ukim.finki.wpaud.service.CategoryService;
import mk.ukim.finki.wpaud.service.ManufacturerService;
import mk.ukim.finki.wpaud.service.ProductService;
import mk.ukim.finki.wpaud.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ManufacturerService manufacturerService;

    @Autowired
    ProductService productService;

    private HtmlUnitDriver driver;

    private static Category c1;
    private static Category c2;
    private static Manufacturer m1;
    private static Manufacturer m2;

    private static User user;
    private static User admin;

    private static String userString = "user";
    private static String admingString = "admin";

    private static boolean dataInitialized = false;


    @BeforeEach
    public void setup(){
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy(){
        if (this.driver != null){
            this.driver.close();
        }
    }

    public void initData(){
        if (!dataInitialized) {
            c1 = categoryService.create("c1", "c1 desc");
            c2 = categoryService.create("c2", "c2 desc");
            m1 = manufacturerService.save("m1", "m1 address").get();
            m2 = manufacturerService.save("m2", "m2 address").get();

            user = userService.register(userString, userString, userString, "user", "user", Role.ROLE_USER);
            admin = userService.register(admingString, admingString, admingString, "admin", "admin", Role.ROLE_ADMIN);
            dataInitialized = true;
        }
    }

    @Test
    public void testScenario() throws Exception{
        ProductsPage productsPage = ProductsPage.to(this.driver);
        productsPage.assertElements(0,0,0,0,0);
        LoginPage loginPage = LoginPage.openLogin(this.driver);
        productsPage = LoginPage.doLogin(this.driver, loginPage, admingString, admingString);
        productsPage.assertElements(0,0,0,0,1);

        productsPage = AddOrEditProduct.addProduct(this.driver, "test", "100", "5", c2.getName(), m2.getName());
        productsPage.assertElements(1, 1, 1, 1, 1);

        productsPage = AddOrEditProduct.addProduct(this.driver, "test1", "200", "4", c1.getName(), m2.getName());
        productsPage.assertElements(2, 2, 2, 2, 1);

        productsPage.getDeleteButtons().get(1).click();
        productsPage.assertElements(1, 1, 1, 1, 1);

        productsPage = AddOrEditProduct.editProduct(this.driver, productsPage.getEditButtons().get(0), "test1", "200", "4", c1.getName(), m2.getName());
        productsPage.assertElements(1, 1, 1, 1, 1);

        loginPage = LoginPage.logout(this.driver);
        productsPage = loginPage.doLogin(this.driver, loginPage, userString, userString);
        productsPage.assertElements(1, 0, 0, 1, 0);

        productsPage.getCartButtons().get(0).click();
        Assert.assertEquals("url do not match", "http://localhost:9999/shopping-cart", this.driver.getCurrentUrl());

        ShoppingCartPage shoppingCartPage = ShoppingCartPage.init(this.driver);
        shoppingCartPage.assertElements(1);

    }

}
