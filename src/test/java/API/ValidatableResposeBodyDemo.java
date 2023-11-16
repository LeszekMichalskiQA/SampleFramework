package API;

import BasePage.BaseUITest;
import io.restassured.parsing.Parser;
import org.junit.Test;

import static org.hamcrest.Matchers.*;


public class ValidatableResposeBodyDemo extends BaseUITest {

    @Test
    public void matcherExample(){
        responseGet(BASE_URL)
                .then()
                .body("current_user_url", equalTo(BASE_URL + "/user"))
                .body(containsString("feeds_url"), containsString("current_user_url"));
    }
    @Test
    public void nestedBodyValidation(){
        responseGet(LIMIT_EP).then()
                .rootPath("resources.core")
                    .body("limit", equalTo(60))
                    .body("remaining", lessThanOrEqualTo(60))
                    .body("reset", notNullValue())
                    .body("used", equalTo(3))
                    .body("resource", equalTo("core"))
                .rootPath("resources.search")
                    .body("limit", equalTo(10))
                    .body("remaining", lessThanOrEqualTo(10))
                .noRootPath();
    }
    @Test
    public void repeatingItems(){
        responseGet(USERS_EP)
                .then()
                .body("data.id[0]", equalTo(1))
                .body("data.id[1]", equalTo(2))
                .body("data.first_name[2]", equalTo("Emma"))
                .body("data.first_name[3]", equalTo("Eve"))
//                .body("data.first_name", containsInAnyOrder("Eve", "Emma"));
//                .body("data.id", hasItems(1, 2, 3, 4, 5, 6));
                .body("data.first_name", hasItem("Eve"))
                .body("data.first_name", hasItems("Eve", "Emma"))
                .body("data.first_name", hasItem(endsWith("ma")));
    }
    @Test
    public void parserAndSyntacticSugar(){

        responseGet(BASE_URL)
                .then()
                .using().defaultParser(Parser.JSON)
                .body("current_user_url", equalTo(BASE_URL + "/user"));
    }
}
