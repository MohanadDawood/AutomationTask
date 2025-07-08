package Dawood.AbstractComponents;

import Dawood.Pageobjects.OrdersPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Dawood.Pageobjects.CartPage;

import java.time.Duration;
import java.util.List;

public class AbstractComponent {

    WebDriver driver;
    public AbstractComponent(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    @FindBy (css = "[routerlink*='cart']")
    WebElement cartHeader;

    @FindBy (css = "[routerlink*='myorders']")
    WebElement ordersHeader;

    //why initialize this method in the AbstractComponent instead of ProductCatalogue because the header components are shared among all the classes
    public CartPage goToCartPage(){
        cartHeader.click();
        CartPage cartPage = new CartPage(driver);
        return cartPage;

    }

    public OrdersPage goToOrdersPage(){
        ordersHeader.click();
        OrdersPage ordersPage = new OrdersPage(driver);
        return ordersPage;
    }


    public void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForElementToAppear(WebElement elem) {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(elem));
    }


    public void waitForElementToDisappear(WebElement element) throws InterruptedException {
//        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
//        wait.until(ExpectedConditions.invisibilityOf(element));
        Thread.sleep(2000);
    }

    public void waitForElementToBeClickable(By findBy){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(findBy));
    }

    public void waitForVisibilityOfAllElements(List<WebElement> findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(findBy));
    }
}
