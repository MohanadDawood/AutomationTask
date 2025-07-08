package Dawood.Tests;

import Dawood.Pageobjects.*;
import Dawood.TestComponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest extends BaseTest {

    String productName = "ADIDAS ORIGINAL";
    @Test(dataProvider = "getData",groups = {"Purchase"})
    //@Test
    //with dataprovider 2D Array
    //public void submitOrder (String email,String password,String productName) throws Exception {

    //with HashMaps
    public void submitOrder (HashMap<String,String> input) throws Exception {



        //the test fail because it's an indexing problem the test keeps adding the first element to cart although the second item is the selected one
        ProductCatalogue productCatalogue = landingPage.ApplicationLogin(input.get("email"),input.get("password"));
        List<WebElement> productList = productCatalogue.getProducts();
//        productList.forEach(p -> System.out.println(p.getText()));

        //I don't need to use this method as it's used in the AddProductToCart method so just call the AddProductToCart method
//        productCatalogue2.getProductName(productName);

        productCatalogue.addProductToCart(input.get("product"));

        CartPage cartPage = productCatalogue.goToCartPage();

        //no need to use this as the cartProducts is in use in checkForProductMatching method also there is no use for it in Code
        //List<WebElement> cartProducts = cartSection.getCartProducts();

        boolean match = cartPage.checkForProductMatching(input.get("product"));
        System.out.println(match);
        Assert.assertTrue(match);

        CheckoutPage checkoutPage = cartPage.checkout();

        checkoutPage.fillPaymentInfo("123","Mohanad Dawood","egy");

        ConfirmationPage confirmationPage = checkoutPage.goToOrderRecievedPage();

        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));

    }

    //Verify product is displaying in orders page
    @Test (dependsOnMethods = {"submitOrder"})
    public void verifyProductDisplay(){
        ProductCatalogue productCatalogue = landingPage.ApplicationLogin("mohaneddawood990@gmail.com","@Dawood1234");
        OrdersPage ordersPage = productCatalogue.goToOrdersPage();
        Boolean match = ordersPage.getProductName(productName);
        Assert.assertTrue(match);
    }

    //Using 2D Arrays
 /*   @DataProvider
    public Object[][]  getData(){
        return new Object[][] {{"mohaneddawood990@gmail.com","@Dawood1234","ADIDAS ORIGINAL"},{"mohaneddawood990@test.com","@Dawood123","ZARA COAT 3"}};
    }*/

    //Using Hashmaps
//    @DataProvider
//    public Object[][]  getData(){
//    HashMap<String,String> map1 = new HashMap<String,String>();
//        map1.put("email","mohaneddawood990@gmail.com");
//        map1.put("password","@Dawood1234");
//        map1.put("product","ZARA COAT 3");
//
//        HashMap<String,String> map2 = new HashMap<String,String>();
//        map2.put("email","mohaneddawood990@test.com");
//        map2.put("password","@Dawood123");
//        map2.put("product","ADIDAS ORIGINAL");
//
//    return new Object[][] {{map1},{map2}};
//    }


    //Create List of hashmaps
    @DataProvider
    public Object[][]  getData() throws IOException {
        List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\Dawood\\data\\PurchaseOrder.json");
        return new Object[][] {{data.get(0)},{data.get(1)}};

    }
}
