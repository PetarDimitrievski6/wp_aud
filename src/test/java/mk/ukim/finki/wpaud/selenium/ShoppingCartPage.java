package mk.ukim.finki.wpaud.selenium;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class ShoppingCartPage extends AbstractPage{
    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "tr[class=cart-item]")
    private List<WebElement> cartRows;

    public static ShoppingCartPage init(WebDriver driver){
        return PageFactory.initElements(driver, ShoppingCartPage.class);
    }
    public void assertElements(int cartItemsNumber){
        Assert.assertEquals("rows do not match", cartItemsNumber, this.getCartRows().size());

    }
}
