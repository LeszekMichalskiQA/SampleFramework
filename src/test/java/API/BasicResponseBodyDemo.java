package API;

import BasePage.BaseUITest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.junit.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;


public class BasicResponseBodyDemo extends BaseUITest{


    @Test
    public void jsonPathReturnsMap(){
        ResponseBody<?> body = response(LIMIT_EP).getBody();
        JsonPath jPath = body.jsonPath();
        JsonPath jsonPath2 = response(LIMIT_EP).jsonPath();

        Map<String, String> fullJson = jsonPath2.get();
        Map<String, String> subMap = jsonPath2.get("resources");
        Map<String, String> subMap2 = jsonPath2.get("resources.core");

        int value = jsonPath2.get("resources.core.limit");
        int value2 = jsonPath2.get("resources.graphql.remaining");

        System.out.println(fullJson);
        System.out.println(subMap);
        System.out.println(subMap2);
        System.out.println(value);
        System.out.println(value2);

        assertEquals(value, 60);
        assertEquals(value2, 0);
    }
    @Test
    public void castingFailure() {
        JsonPath jPath = response(LIMIT_EP).body().jsonPath();

        Map<String, String> isNull = jPath.get("incorrect.path"); //NPE
        int aMap = jPath.get("resources.core");                   //ClassCastException
        String value = jPath.get("resources.core.limit");         //ClassCastException
    }
}
