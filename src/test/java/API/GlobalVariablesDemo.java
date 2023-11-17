package API;

import BasePage.BaseUITest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.listener.ResponseValidationFailureListener;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


import static RestAssuredConfig.ResponseSpec.badEndpointSpec;
import static io.restassured.config.FailureConfig.failureConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.hamcrest.Matchers.equalTo;

public class GlobalVariablesDemo extends BaseUITest {

    static RequestSpecification spec = new RequestSpecBuilder().setBaseUri(BASE_URL).build();

    //alternative way to set up base url
    @BeforeSuite
    void setUpSpecification(){
        RestAssured.requestSpecification = new RequestSpecBuilder().setBaseUri(BASE_URL).build();
    }
    @BeforeClass
    void setup(){
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectContentType(ContentType.JSON)
                .build();
    }
    //or
    ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectContentType(ContentType.JSON)
                .build();
    @AfterClass
    void cleanUp(){
        RestAssured.responseSpecification = null;
    }
    @Test
    public void testOneUsingGlobalConstants(){
        RestAssured.get()
                .then()
                .body("id[0]", equalTo(1));
    }

    @Test
    public void testTwoUsingGlobalConstants(){
        RestAssured.get()
                .then()
                .body("id[1]", equalTo(2));
    }
    @Test
    void maxRedirectsTest(){
        RestAssured.config = RestAssured.config().redirect(redirectConfig().followRedirects(false));

        responseGet(BASE_URL + "/repos/twitter/bootstrap")
                .then()
                .statusCode(equalTo(200));
    }
    @Test
    void failureConfigExample(){

        ResponseValidationFailureListener failureListener = (reqSpec, resSpec, response) ->
                System.out.printf("We have a failure, response status was %s and the body contained: %s",
                        response.statusCode(), response.body().asPrettyString());

        RestAssured.config = RestAssured.config().failureConfig(failureConfig().failureListeners(failureListener));

        RestAssured.get(BASE_URL + "/users/andrejs-ps")
                .then()
                .body("some.path", equalTo("a thing"));
    }
    @Test
    void testUsingLocalRequestSpec(){
        RestAssured
                .given()
                    .spec(spec)
                .when()
                    .get()
                .then()
                    .statusCode(200);
    }
    @Test
    void testWithSpecOne(){
        responseGet(BASE_URL + "/non/existing/url")
                .then()
                .spec(badEndpointSpec())
                .body("message", equalTo("Not Found"));
    }
    @Test
    void testWithSpecTwo(){
        responseGet(BASE_URL + "/non/existing/url")
                .then()
                .spec(badEndpointSpec())
                .body("documentation_url", equalTo("https://developer.github.com/v3"));
    }
    @Test
    void testWithSpecThree(){
        responseGet(BASE_URL + "/non/existing/url")
                .then()
                .spec(responseSpecification);
    }
    @Test
    void logTest(){
        RestAssured
                .given()
                    .log().all()
                //or
                .given()
                    .log().headers()
                    .log().body()
                .when()
                    .get(POSTS_EP)
                .then()
                    .log().headers()
                // with condition
                    .log().ifValidationFails(LogDetail.ALL)
                    .statusCode(200);
    }

}
