package Dawood.Pageobjects;

import Dawood.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//POM design pattern
public class LandingPage extends AbstractComponent {


    WebDriver driver;


    public LandingPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        //used to initialize all the pageFactory elements
        PageFactory.initElements(driver,this);
    }

   //WebElement userEmail = driver.findElement(By.xpath("//input[@id='userEmail']"));
   //WebElement userPassword = driver.findElement(By.xpath("//input[@id='userPassword']"));

    //PageFactory design pattern --> PageFactory elements
    @FindBy (xpath = "//input[@id='userEmail']")
    WebElement userEmail;

    @FindBy (xpath = "//input[@id='userPassword']")
    WebElement userPassword;

    @FindBy (xpath = "//input[@id='login']")
    WebElement submit;

    @FindBy (css = "div[aria-label='Incorrect email or password.']")
    WebElement errorMessage;

    //here we changed the return type to object as we are returning the productCatalogue2 object
    public ProductCatalogue ApplicationLogin(String username, String password){
        userEmail.sendKeys(username);
        userPassword.sendKeys(password);
        submit.click();
        ProductCatalogue productCatalogue = new ProductCatalogue(driver);
        return productCatalogue;

    }

    public void goTo(){
        driver.get("https://rahulshettyacademy.com/client");
    }

    public WebElement getErrorMessage(){
        waitForElementToAppear(errorMessage);
        return errorMessage;
    }

}
