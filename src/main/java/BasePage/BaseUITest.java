package BasePage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseUITest {
    protected static final String BASE_URL = "https://api.github.com";
    protected static final String REPO_EP = BASE_URL + "/repos";
    protected static final String LIMIT_EP = BASE_URL + "/rate_limit";
    protected static final String USERS_EP = "https://reqres.in/api/users?page=1";
    public Response response(String url, String user, String repo) {
        return RestAssured.get(url, user, repo);
    }
    public Response response(String url) {
        return RestAssured.get(url);
    }
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
