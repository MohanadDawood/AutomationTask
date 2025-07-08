package Dawood.Pageobjects;

import Dawood.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrdersPage extends AbstractComponent {
    public OrdersPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

        @FindBy (css = "tbody td:nth-child(3)")
        List<WebElement> productList;

    public Boolean getProductName(String productName) {
        Boolean match = productList.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
        return  match;
    }


}
