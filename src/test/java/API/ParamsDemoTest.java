package API;

import BasePage.BaseUITest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.util.Map;


public class ParamsDemoTest extends BaseUITest {

    static final String BASE_URL = "https://api.github.com/search/repositories";

    @Test
    void withoutParam(){
        response(BASE_URL + "?q=java")
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    @Test
    void withoutParam2(){
       response(BASE_URL + "?q=java&per_page=1")
               .prettyPeek()
               .then()
               .statusCode(200);
    }
    @Test
    void withParam(){
        RestAssured
                .given()
                .param("q", "java")
                .param("per_page", 1)
                .get(BASE_URL)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    @Test
    void withParamAsMap(){
        RestAssured
                .given()
                .params(Map.of("q", "java", "per_page", 1))
                .get(BASE_URL)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
