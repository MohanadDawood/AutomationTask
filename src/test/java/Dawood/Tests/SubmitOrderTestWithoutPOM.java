package Dawood.Tests;

import Dawood.Pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class SubmitOrderTestWithoutPOM {
    public static void main (String[]args) throws Exception {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/client");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        driver.manage().window().maximize();

        LandingPage landingPage = new LandingPage(driver);
        driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys("mohaneddawood990@gmail.com");
        driver.findElement(By.xpath("//input[@id='userPassword']")).sendKeys("@Dawood1234");
        driver.findElement(By.xpath("//input[@id='login']")).click();


        List<WebElement> productList =  driver.findElements(By.xpath("//div[@class='card-body']/h5/b"));

//        WebElement product = driver.findElement(By.xpath("//div[@class='col-lg-4 col-md-6 col-sm-10 offset-md-0 offset-sm-1 mb-3 ng-star-inserted']"));
        //WebElement productText = productsText.stream().filter(product -> product.findElement(By.xpath(".//h5/b")).getText().equalsIgnoreCase("ADIDAS ORIGINAL")).findFirst().orElse(null);

        productList.stream()
                .filter(productElement -> productElement.getText().equalsIgnoreCase("ADIDAS ORIGINAL"))
                .findFirst()
                .ifPresent(productElement -> {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='toast-container']")));
                    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
                    int index = productList.indexOf(productElement);
                    driver.findElements(By.xpath("//button[@class='btn w-10 rounded']")).get(index).click();
                });


//        productText.findElement(By.xpath("//button[@class='btn w-10 rounded']")).click();

        //after that we wait for the toast message container to appear/the message so we can grab the text and make an assertion on it that the item is added to the cart successfully
       // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='toast-container']")));

        //what are waiting for here is the animation that appears on the screen after clicking on the addtocart button to be invisible/disappear why ? because you can't access/click the cart until this animation disappears
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//ng-animation")));

        //this improves the performance alot instead of waiting for invisibility of element located to disappear

        //wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();




        List<WebElement> cartProducts = driver.findElements(By.xpath("//*[@class='cartSection']/h3"));



        //why to use anyMatch method ? because filter method returns the product and we actually do not need to return the element so anymatch searches for the product if any is retrieved it just returns a bolean value of true if matched and false if not , we just need a condition not the actual element
        //boolean match = cartProducts.stream().anyMatch(product -> product.getText().equalsIgnoreCase("ADIDAS ORIGINAL"));

        //if it matches so the assertion will be pass
        //Assert.assertTrue(match);

        driver.findElement(By.xpath("//button[contains(text(),'Checkout')]")).click();


        driver.findElement(By.xpath("//div[@class='field small']/input[@class='input txt']")).sendKeys("123");

        driver.findElement(By.xpath("//div[contains(text(),'Name on Card ')]/following-sibling::input")).sendKeys("Mohanad Dawood");

        Actions action = new Actions(driver);
        action.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")),"egypt").build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.xpath("//span[contains(text(),\" Egypt\")]")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btnn action__submit ng-star-inserted']")));
        driver.findElement(By.xpath("//a[@class='btnn action__submit ng-star-inserted']")).click();

        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
        driver.close();

    }
}
