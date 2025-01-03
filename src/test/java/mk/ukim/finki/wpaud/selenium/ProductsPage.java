package mk.ukim.finki.wpaud.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
@Getter
public class ProductsPage extends AbstractPage{
    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "tr[class=product]")
    private List<WebElement> productRows;

    @FindBy(css = ".delete-product")
    private List<WebElement> deleteButtons;

    @FindBy(className = "edit-product")
    private List<WebElement> editButtons;

    @FindBy(css = ".cart-product")
    private List<WebElement> cartButtons;

    @FindBy(css = ".add-product-btn")
    private List<WebElement> addProductButton;

    public static ProductsPage to(WebDriver driver){
        get(driver, "/products");
        return PageFactory.initElements(driver, ProductsPage.class);
    }
    public void assertElements(int productsNumber, int deleteButtons, int editButtons, int cardButtons, int addButtons){
        Assert.assertEquals("rows do not match", productsNumber, this.getProductRows().size());
        Assert.assertEquals("delete do not match", deleteButtons, this.getDeleteButtons().size());
        Assert.assertEquals("edit do not match", editButtons, this.getEditButtons().size());
        Assert.assertEquals("cart do not match", cardButtons, this.getCartButtons().size());
        Assert.assertEquals("add is visible", addButtons, this.getAddProductButton().size());

    }
}
