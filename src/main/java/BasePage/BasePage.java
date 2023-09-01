package BasePage;

import com.beust.ah.A;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Function;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public Actions actions;
    public Select select;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    public <T> T wait(Function<WebDriver, T> expectedConditions, int timeoutInSeconds){
        return(new WebDriverWait(this.driver, Duration.ofSeconds(timeoutInSeconds)))
                .pollingEvery(Duration.ofSeconds(1L))
                .ignoring(NoSuchElementException.class).until(expectedConditions);
    }
    public <T> T wait(Function<WebDriver, T> expectedConditions){
        return(new WebDriverWait(this.driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1L))
                .ignoring(NoSuchElementException.class).until(expectedConditions));
    }
    public void  waitForPageToLoad(){
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
    public Actions action(){
        return actions;
    }
    public Select select(WebElement element){
        return new Select(element);
    }
}
