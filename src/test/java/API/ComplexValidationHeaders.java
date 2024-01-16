package API;

import BasePage.BaseUITest;
import org.hamcrest.Matchers;
import org.hamcrest.number.OrderingComparison;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ComplexValidationHeaders extends BaseUITest {

    @Test
    public void complexHamcrestMatchersHeaders_1(){
        responseGet(LIMIT_EP)
                .then()
                .header("x-ratelimit-limit", equalTo("60"))
                .header("x-ratelimit-limit", Integer::parseInt, equalTo(60));
    }
    @Test
    public void complexHamcrestMatchersHeaders_2(){
        responseGet(LIMIT_EP)
                .then()
                .header("date", date -> LocalDate.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME),
                        OrderingComparison.comparesEqualTo(LocalDate.now()));
    }
    @Test
    public void complexHamcrestMatchersHeaders_3(){
        responseGet(LIMIT_EP)
                .then()
                .header("x-ratelimit-limit",
                    response -> greaterThan(response.header("x-ratelimit-remaining")));
    }
    @Test
    public void complexBodyExample(){
        responseGet(BASE_URL + "/users/andrejs-ps")
                .then()
                .body("url", response -> Matchers.endsWith(response.body().jsonPath().get("login")));
    }
    @Test
    public void basicExample(){
        responseGet(POSTS_EP)
                .then()
                .body(matchesJsonSchemaInClasspath("basic_example.json"));
    }
}
