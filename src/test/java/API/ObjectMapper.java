package API;

import BasePage.BaseUITest;
import Users.User;
import org.junit.jupiter.api.Test;
import org.testng.Assert;


public class ObjectMapper extends BaseUITest {

    @Test
    void objectMappingDemon(){
        User user = responseGet(REST_ASSURED).as(User.class);

        Assert.assertEquals(user.getLogin(), "rest-assured");
        Assert.assertEquals(user.getId(), 1231231);
        Assert.assertEquals(user.getPublicRepos(), 2);
    }
}
