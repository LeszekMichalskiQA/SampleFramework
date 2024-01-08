package pages;

import BasePage.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@id='user-name']")
    private WebElement usernameField;
    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordField;
    @FindBy(xpath = "//input[@id='login-button']")
    private WebElement loginButton;
    @FindBy(xpath = "//div[contains(text(), 'Products')]")
    List<WebElement> productsField;
    @FindBy(xpath =  "//h3[@data-test='error']")
    List<WebElement> errorMessageByList;

    @FindBy(xpath =  "//h3[@data-test='error']")
    WebElement errorMessage;
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open(String url) {
        driver.get(url);
        return this;
    }

    @Step("Login to the Application")
    public ProductsPage loginUser(String user, String password, SoftAssert softAssert) {
        waitForPageToLoad(30);
        usernameField.sendKeys(user);
        passwordField.sendKeys(password);
        loginButton.click();
        if(doElementsExists(errorMessageByList)){
            String errorText = errorMessage.getAttribute("textContent");
            softAssert.assertTrue(errorText.contains("Epic sadface: "), "Error message was NOT present");
        }
        softAssert.assertTrue(!productsField.isEmpty(), "'Products' header was NOT displayed");
        return new ProductsPage(driver);
    }

}
