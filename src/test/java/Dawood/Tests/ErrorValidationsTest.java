package Dawood.Tests;

import Dawood.Pageobjects.CartPage;
import Dawood.Pageobjects.ProductCatalogue;
import Dawood.TestComponents.BaseTest;
import Dawood.TestComponents.Retry;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ErrorValidationsTest extends BaseTest {

    @Test(groups= {"ErrorHandling"}, retryAnalyzer = Retry.class)
    public void LoginValidationError () throws Exception {

        landingPage.ApplicationLogin("mohaneddawood99988@outlook.com","@Dawooood1234");
        String errorMessage = landingPage.getErrorMessage().getText();
        Assert.assertEquals(errorMessage, "Incorrect email password.");
    }

    public void ProductErrorValidation() throws Exception {
        String productName = "ZARA COAT 33333";
        ProductCatalogue productCatalogue = landingPage.ApplicationLogin("mohaneddawood990@gmail.com","@Dawood1234");
        List<WebElement> productList = productCatalogue.getProducts();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();
        boolean match = cartPage.checkForProductMatching(productName);
        Assert.assertFalse(match);
    }
}
