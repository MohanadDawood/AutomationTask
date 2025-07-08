package Dawood.Pageobjects;

import Dawood.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponent {

    WebDriver driver;


    @FindBy(xpath = "//button[contains(text(),'Checkout')]")
    WebElement checkout;

    @FindBy(css = ".cartSection h3")
    private List<WebElement> cartProducts;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }



    public boolean checkForProductMatching(String productName) {
        // Wait for the cart products to be present
        waitForVisibilityOfAllElements(cartProducts);
        // Log the products in the cart
//        for (WebElement prod : cartProducts) {
//            System.out.println("Product in cart: " + prod.getText());
//        }

        // Check if any product matches the provided product name
        boolean match = cartProducts.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
//        System.out.println("Product match result: " + match);
        return match;
    }

    public CheckoutPage checkout() {
        checkout.click();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        return checkoutPage;
    }



}
