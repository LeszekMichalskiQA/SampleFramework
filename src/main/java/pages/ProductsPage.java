package pages;

import BasePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ProductsPage extends BasePage {
    @FindBy(xpath = "//button[text()='Open Menu']")
    private WebElement menuButton;
    @FindBy(xpath = "//a[@id='logout_sidebar_link']")
    private WebElement logOutButton;
    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage goBackToLoginPageByLogout(){
        waitForPageToLoad();
        menuButton.click();
        wait(elementToBeClickable(logOutButton), 15).click();
        waitForPageToLoad();
        return new LoginPage(driver);
    }
}
