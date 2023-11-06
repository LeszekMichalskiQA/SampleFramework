package BasePage;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseUITest {

    protected WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }
    @AfterClass
    public void tearDown(){
        driver.close();
        driver.quit();
    }
}
