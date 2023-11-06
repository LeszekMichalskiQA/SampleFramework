package RestAssured_API;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertEquals;

public class Demo {
    static final String BASE_URL = "https://api.github.com";
    Response response = RestAssured.get(BASE_URL);

    @Test
    void someTest(){
        RestAssured.get("https://api.github.com")
                .then()
                .statusCode(200);
    }
    @Test
    void peek(){
        RestAssured.get(BASE_URL)
                .peek();
    }
    @Test
    void prettyPeek(){
        RestAssured.get(BASE_URL)
                .prettyPeek();
    }
    @Test
    void prettyPrint(){
        RestAssured.get(BASE_URL)
                .prettyPrint();
    }
    @Test
    void convenienceMethods(){
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8");
    }
    @Test
    void genericHeader(){
       assertEquals(response.getHeader("server"), "Github.com");
       assertEquals(response.getHeader("x0rate-limit"), "60");

       //OR
        assertEquals(Integer.parseInt(response.getHeader("x-rate-limit")), 60);
    }
    @Test
    void validatableResponse(){
        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("x-ratelimit-limit", "60");
    }
    @Test
    void hamcrest(){
        response.then()
                .header("etag", notNullValue())
                .header("etag", not(emptyString()))
                .statusCode(anyOf(equalTo(200), equalTo(202)));
    }
    @Test
    void simpleHamrestMatchers(){
        response.then()
                .statusCode(200)
                .statusCode(lessThan(300))
                .header("cache-control", containsStringIgnoringCase("max-age=60"))
                .time(lessThan(2L), TimeUnit.SECONDS)
                .header("etag", notNullValue())
                .header("etag", is(emptyString()));
    }
}
