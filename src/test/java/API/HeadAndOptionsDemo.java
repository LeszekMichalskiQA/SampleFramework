package API;

import BasePage.BaseUITest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;


public class HeadAndOptionsDemo extends BaseUITest {

    @Test
    public void headTest(){
        RestAssured.head(BASE_URL)
                .then()
                .statusCode(200)
                .body(Matchers.emptyOrNullString());
    }
    @Test
    public void optionsTest(){
        RestAssured.options(BASE_URL)
                .then()
                .statusCode(204)
                .header("access-control-allow-methods", "GET, POST, PATCH, PUT, DELETE")
                .body(Matchers.emptyOrNullString());
    }
    @Test(description = "Create a repo")
    public void postTest(){
        RestAssured
                .given()
                .auth()
                .oauth2("TOKEN")
//                    .header("Authorization", "token " + TOKEN)
                    .body("{\"name\": \"deleteme\"}")
                .when()
                .post(REPOS_EP)
                    .then()
                .statusCode(201);
    }
    @Test(description = "Update a repo")
    public void patchTest(){
        RestAssured
                .given()
                    .header("Authorization", "token " + "TOKEN")
                    .body("{\"name\": \"deleteme-patched\"}")
                .when()
                    .patch("https://api.github.com/repos/LeszekMichalskiQA/deleteme")
                    .then()
                .statusCode(200);

    }
    @Test(description = "Delete a repo")
    public void deleteTest(){
        RestAssured
                .given()
                    .header("Authorization", "token " + "TOKEN")
                .when()
                    .delete("https://api.github.com/repos/LeszekMichalskiQA/deleteme-patched")
                .then()
                     .statusCode(204);

    }
    @Test
    void customRequest(){

    }
}
