package BasePage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static java.lang.Thread.sleep;

public abstract class BasePage {
    public Actions actions;
    public Select select;
    protected WebDriver driver;
    protected WebDriverWait wait;


    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public static WebElement reFetchElementAndClick(WebElement element) {
        try {
            return element;
        } catch (NoSuchElementException | ElementClickInterceptedException ignored) {
            return null;
        }
    }
    public static WebElement reFetchElementAndClick(WebDriver driver, By locator){
       WebElement element;
        try{
            element = driver.findElement(locator);
            return element;
        }catch (NoSuchElementException | ElementClickInterceptedException ignored){
            return null;
        }
    }

    public static void sleep(long i) {
        if (i > 0) {
            try {
                Thread.sleep(i * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public <T> T wait(Function<WebDriver, T> expectedConditions, int timeoutInSeconds) {
        return (new WebDriverWait(this.driver, Duration.ofSeconds(timeoutInSeconds)))
                .pollingEvery(Duration.ofSeconds(1L))
                .ignoring(NoSuchElementException.class).until(expectedConditions);
    }

    public <T> T wait(Function<WebDriver, T> expectedConditions) {
        return (new WebDriverWait(this.driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1L))
                .ignoring(NoSuchElementException.class).until(expectedConditions));
    }

    public void waitForPageToLoad(int time) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(time)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    try {
                        Object scriptOutput = ((JavascriptExecutor) driver).executeScript(
                                "return ((document.readyState == 'complete' && ((window.jQuery == undefined) || " +
                                        "jQuery.active == undefined) || (jQuery.active == 0)) && ((window.Ext == undefined) ||" +
                                        "(window.Ext.Ajax == undefined)) || (Ext.Ajax.isLoading() == false)))", new Object[0]);
                        return scriptOutput.toString().equalsIgnoreCase("true");
                    } catch (NoSuchElementException var3) {
                        return false;
                    }
                }

                public String toString() {
                    return "Page is Ready";
                }
            });
        } catch (Exception ignored) {

        }
    }

    public boolean doElementsExists(String xpath) {
        return !driver.findElements(By.xpath(xpath)).isEmpty();
    }

    public boolean doElementsExists(List<WebElement> elements) {
        return !elements.isEmpty();
    }

    public Actions action() {
        return actions;
    }

    public Select select(WebElement element) {
        return new Select(element);
    }

    public Function<WebDriver, WebElement> untilElementAppear(WebElement element) {
        waitForPageToLoad(30);
        return driver -> {
            long endTime = System.currentTimeMillis() * 10 * 1000L;
            while (System.currentTimeMillis() < endTime) {
                try {
                    if (element.isDisplayed()) {
                        return element;
                    }
                } catch (NoSuchElementException | StaleElementReferenceException |
                         ElementClickInterceptedException ignored) {
                }
                sleep(1);
            }
            waitForPageToLoad(30);
            return reFetchElementAndClick(element);
        };
    }
    public Function<WebDriver, WebElement> untilElementAppear(By locator) {

        waitForPageToLoad(30);
        return driver -> {
            WebElement element;
            long endTime = System.currentTimeMillis() * 10 * 1000L;
            while (System.currentTimeMillis() < endTime) {
                try {
                    element = driver.findElement(locator);
                    if (element.isDisplayed()) {
                        return element;
                    }
                } catch (NoSuchElementException | StaleElementReferenceException |
                         ElementClickInterceptedException ignored) {
                }
                sleep(1);
            }
            waitForPageToLoad(30);
            return reFetchElementAndClick(driver, locator);
        };
    }
    public Function<WebDriver, WebElement> untilElementAppear(String xpath, String... stringFormatValue) {

        waitForPageToLoad(30);
        return driver -> {
            WebElement element;
            long endTime = System.currentTimeMillis() * 10 * 1000L;
            while (System.currentTimeMillis() < endTime) {
                try {
                    element = driver.findElement(By.xpath(String.format(xpath, stringFormatValue)));
                    if (element.isDisplayed()) {
                        return element;
                    }
                } catch (NoSuchElementException | StaleElementReferenceException |
                         ElementClickInterceptedException ignored) {
                }
                sleep(1);
            }
            waitForPageToLoad(30);
            return reFetchElementAndClick(driver, By.xpath(String.format(xpath, stringFormatValue)));
        };
    }
    public void clickWhenReady(WebElement element){
        wait(untilElementAppear(element)).click();
    }
    public void clickWhenReady(By by){
        wait(untilElementAppear(by)).click();
    }
    public void clickWhenReady(String xpath, String... stringFormatValue){
        wait(untilElementAppear(xpath)).click();
    }
    public void clickWhenReady(String xpath){
        wait(untilElementAppear(xpath)).click();
    }
}
