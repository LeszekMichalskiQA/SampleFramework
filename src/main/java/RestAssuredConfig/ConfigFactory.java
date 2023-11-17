package RestAssuredConfig;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.listener.ResponseValidationFailureListener;

import static io.restassured.config.FailureConfig.failureConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;

public class ConfigFactory {
    public static RestAssuredConfig getAnotherCustomConfig(){
        return RestAssured.config();
    }
    public static RestAssuredConfig getDefaultConfig(){
        ResponseValidationFailureListener failureListener = (reqSpec, respSpec, resp) ->
            System.out.printf("We have a failure, response status was %s and the body contained: %s",
                    resp.getStatusCode(), resp.body().asPrettyString());

        return RestAssured.config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
                .redirect(redirectConfig().maxRedirects(1))
                .failureConfig(failureConfig().failureListeners(failureListener));
    }
}
