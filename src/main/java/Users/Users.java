package Users;

import java.util.LinkedHashMap;
import java.util.Map;

public class Users {

    public static Map<String, String> usersAndPasswords() {
        Map<String, String> usersAndPassword = new LinkedHashMap<>();{

            usersAndPassword.put("standard_user", "secret_sauce");
            usersAndPassword.put("locked_out_user", "secret_sauce");
            usersAndPassword.put("problem_user", "secret_sauce");
            usersAndPassword.put("performance_glitch_user", "secret_sauce");
        }
        return usersAndPassword;
    }
}
