package Dawood.Pageobjects;

import Dawood.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

//POM design pattern
public class ProductCatalogue extends AbstractComponent {


    WebDriver driver;
    int index;

    public ProductCatalogue(WebDriver driver ){
        super(driver);
        this.driver = driver;
        this.index = 0;
        //used to initialize all the pageFactory elements
        PageFactory.initElements(driver,this);
    }

    @FindBy (css = ".mb-3")
    List<WebElement> products;

    @FindBy (css = ".ng-animating")
    WebElement spinner;

    By productsBy = By.cssSelector(".mb-3");

    By addToCartButton = By.cssSelector(".card-body button:last-of-type");

    By toastMessage = By.cssSelector("#toast-container");



    public List<WebElement> getProducts(){
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductName(String productName){


        WebElement prod = getProducts().stream().filter(product->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
        return prod;

    }


    public void addProductToCart(String productName) throws Exception  {
        WebElement prod = getProductName(productName);
        System.out.println("Adding product to cart: " + productName);
        System.out.println("index from addProductToCart method before clicking   "+index);
        prod.findElement(addToCartButton).click();
        waitForElementToAppear(toastMessage);
        waitForElementToDisappear(spinner);
        System.out.println("index from addProductToCart method after reset  " +  index);
    }
}
