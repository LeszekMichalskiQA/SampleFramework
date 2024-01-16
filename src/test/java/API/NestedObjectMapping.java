package API;

import BasePage.BaseUITest;
import RestAssuredConfig.RateLimit;
import Users.User;
import io.restassured.mapper.ObjectMapperType;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NestedObjectMapping extends BaseUITest {

    @Test
    public void nestedObjectMapping() {

        RateLimit limit = responseGet(LIMIT_EP).as(RateLimit.class);
        assertEquals(limit.getCoreLimit(), 59);
        assertEquals(limit.getSearchLimit(), 10);
    }

    @Test
    public void objectMappingRelyingOnMapperType() {

        User user = responseGet(LIMIT_EP).as(User.class, ObjectMapperType.JACKSON_2);

        assertEquals(user.getLogin(), "rest-assured");
    }
}
