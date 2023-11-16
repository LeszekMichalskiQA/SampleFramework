package BasePage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseUITest {
    protected static final String BASE_URL = "https://api.github.com";
    protected static final String REPO_EP = BASE_URL + "/repos";
    protected static final String LIMIT_EP = BASE_URL + "/rate_limit";
    protected static final String REPOS_EP = BASE_URL + "/user/repos";
    protected static final String USERS_EP = "https://reqres.in/api/users?page=1";
   protected static final String POSTS_EP = "https://jsonplaceholder.typicode.com/posts";
    public Response responseGet(String url, String user, String repo) {

        return RestAssured.get(url, user, repo);
    }
    public Response responseGet(String url) {
        return RestAssured.get(url);
    }
    protected WebDriver driver;
    @BeforeSuite
    public void setBaseUrl(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "api/users";
        RestAssured.rootPath = "data";
    }

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
