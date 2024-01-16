package BasePage;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static RestAssuredConfig.ConfigFactory.getDefaultConfig;
import static io.restassured.RestAssured.DEFAULT_URI;
import static org.apache.http.HttpStatus.*;

public class BaseUITest {
    protected static final String BASE_URL = "https://api.github.com";
    protected static final String REPO_EP = BASE_URL + "/repos";
    protected static final String LIMIT_EP = BASE_URL + "/rate_limit";
    protected static final String REPOS_EP = BASE_URL + "/user/repos";
    protected static final String USERS_EP = "https://reqres.in/api/users?page=1";
    protected static final String POSTS_EP = "https://jsonplaceholder.typicode.com/posts";
    protected static final String REST_ASSURED = BASE_URL + "/users/rest-assured";
    protected WebDriver driver;

    public Response responseGet(String url, String user, String repo) {
        return RestAssured.get(url, user, repo);
    }

    public Response responseGet(String url) {
        return RestAssured.get(url);
    }

    @BeforeSuite
    public void suiteSetup() {
        RestAssured.config = getDefaultConfig();
    }
    @BeforeMethod()
    public void setUpURL(){
        RestAssured.baseURI = BASE_URL;
    }

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }
    @AfterMethod
    void cleanUp(){
        RestAssured.baseURI = DEFAULT_URI;
    }

    @AfterClass
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
