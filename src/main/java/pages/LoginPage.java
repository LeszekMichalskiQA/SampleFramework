package pages;

import BasePage.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@id='user-name']")
    private WebElement usernameField;
    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordField;
    @FindBy(xpath = "//input[@id='login-button']")
    private WebElement loginButton;
    @FindBy(xpath = "//div[contains(text(), 'Products')]")
    private WebElement productsField;
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open(String url) {
        driver.get(url);
        return this;
    }

    @Step("Login to the Application")
    public ProductsPage loginUser(String user, String password, SoftAssert softAssert) {
        waitForPageToLoad();
        usernameField.sendKeys(user);
        passwordField.sendKeys(password);
        loginButton.click();
        softAssert.assertTrue(productsField.isDisplayed(), "'Products' header was NOT displayed");
        return new ProductsPage(driver);
    }

}
