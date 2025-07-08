package Dawood.Pageobjects;

import Dawood.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends AbstractComponent {

    WebDriver driver;
    public CheckoutPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    @FindBy (xpath = "//div[@class='field small']/input[@class='input txt']")
    WebElement cvvCode;

    @FindBy (xpath = "//div[contains(text(),'Name on Card ')]/following-sibling::input")
    WebElement nameOnCard;

    @FindBy (xpath = "//input[@placeholder='Select Country']")
    WebElement countrySelection;

    @FindBy (xpath = "//span[contains(text(),\" Egypt\")]")
    WebElement selectedCountry;

    By countrySelectionMenu = By.cssSelector(".ta-results");

    By placeholder = By.xpath("//a[@class='btnn action__submit ng-star-inserted']");

    public void fillPaymentInfo(String cvv, String cardName, String countryInput){

        cvvCode.sendKeys(cvv);
        nameOnCard.sendKeys(cardName);
        Actions action = new Actions(driver);
        action.sendKeys(countrySelection,countryInput).build().perform();
        waitForElementToAppear(countrySelectionMenu);
        selectedCountry.click();
    }

    public ConfirmationPage goToOrderRecievedPage(){
        waitForElementToBeClickable(placeholder);
        driver.findElement(placeholder).click();
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        return confirmationPage;
    }
}
