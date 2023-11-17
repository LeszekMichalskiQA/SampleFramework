package API;

import BasePage.BaseUITest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.testng.Assert;

import static org.hamcrest.CoreMatchers.*;

public class FluentInterface extends BaseUITest {

    @Test
    public void comparison(){

        String value = responseGet("")
                .body().jsonPath().get("property");

        Assert.assertEquals(value, "");

        responseGet("")
                .then()
                .assertThat()
                .body("property", Matchers.equalTo("abc"));
    }
    @Test
    public void complexHamcrestMatchersHeaders_1(){
        responseGet(BASE_URL + "/rate_limit")
                .then()
                .header("x-ratelimit-limit", equalTo("60"))
                .header("x-ratelimit-limit", s -> Integer.parseInt(s),equalTo("60"))
                .header("x-ratelimit-limit", Integer::parseInt, equalTo("60"));
    }
    @Test
    public void complexHamcrestMatchersHeaders_2() {
    }
}
