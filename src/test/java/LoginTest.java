import BasePage.BaseUITest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;

import java.util.Map;

import static Users.Users.usersAndPasswords;

public class LoginTest extends BaseUITest {


    @Test
    public void loginTestForAllAccounts() {
        LoginPage loginPage = new LoginPage(driver);
        SoftAssert softAssert = new SoftAssert();

        loginPage
                .open("https://www.saucedemo.com/v1/");

        for (Map.Entry<String, String> user : usersAndPasswords().entrySet()) {
            loginPage
                    .loginUser(user.getKey(), user.getValue(), softAssert)
                    .goBackToLoginPageByLogout();
        }
        softAssert.assertAll();
    }
}
