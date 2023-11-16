package API;

import BasePage.BaseUITest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertEquals;

public class DemoTest extends BaseUITest {


    @Test
    void someTest() {
        RestAssured.get(BASE_URL)
                .then()
                .statusCode(200);
    }
    @Test
    void peek() {
        RestAssured.get(BASE_URL)
                .peek();
    }
    @Test
    void prettyPeek() {
        RestAssured.get(BASE_URL)
                .prettyPeek();
    }

    @Test
    void prettyPrint() {
        RestAssured.get(BASE_URL)
                .prettyPrint();
    }

    @Test
    void convenienceMethods() {
        Assert.assertEquals(responseGet(BASE_URL).getStatusCode(), 201);
        Assert.assertEquals(responseGet(BASE_URL).getContentType(), "application/json; charset=utf-8");
    }
    @Test
    void genericHeader() {
        assertEquals(responseGet(BASE_URL).getHeader("server"), "Github.com");
        assertEquals(responseGet(BASE_URL).getHeader("x0rate-limit"), "60");

        //OR
        assertEquals(Integer.parseInt(responseGet(BASE_URL).getHeader("x-rate-limit")), 60);
    }

    @Test
    void validatableResponse() {
        responseGet(BASE_URL).then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("x-ratelimit-limit", "60");
    }

    @Test
    void hamcrest() {
        responseGet(BASE_URL).then()
                .header("etag", notNullValue())
                .header("etag", not(emptyString()))
                .statusCode(anyOf(equalTo(200), equalTo(202)));
    }

    @Test
    void simpleHamrestMatchers() {
        responseGet(BASE_URL).then()
                .statusCode(200)
                .statusCode(lessThan(300))
                .header("cache-control", containsStringIgnoringCase("max-age=60"))
                .time(lessThan(2L), TimeUnit.SECONDS)
                .header("etag", notNullValue())
                .header("etag", is(emptyString()));
    }

    @Test
    void withoutParamHardcoded() {
        responseGet(REPO_EP + "/andrejs-ps/automated-web-testing-in-java-with-playwright")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(412446871));

        responseGet(REPO_EP + "/andrejs-ps/automated-web-testing-in-java-with-playwright")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(412446871));
    }

    @Test
    void withOverLoadedGet() {
        responseGet(REPO_EP + "/{user}/{repo}", "andrejs-ps", "automated-web-testing-in-java-with-playwright")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(412446871));
    }



    @Test
    void withParam() {
        RestAssured
                .given()
                .pathParam("user", "andrejs-ps")
                .pathParam("repo_name", "automated-web-testing-in-java-with-playwright")
                    .get(REPO_EP + "/{user}/{repo_name}")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(412446871));
    }
    @Test
    void withParamAsMap(){
        Map<String, String> reusableMap = Map.of(
                "user", "andrejs-ps",
                "repo_name", "automated-web-testing-in-java-with-playwright");

        RestAssured
                .given()
                .pathParams(reusableMap)
                .get(REPO_EP + "/{user}/{repo_name}")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(412446871));
    }
}
