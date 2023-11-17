package RestAssuredConfig;

import BasePage.BaseUITest;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;

public class ResponseSpec extends BaseUITest {
    public static ResponseSpecification badEndpointSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(SC_OK)
                .expectContentType(JSON)
                .build();
    }
}
