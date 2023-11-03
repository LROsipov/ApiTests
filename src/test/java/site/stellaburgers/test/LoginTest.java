package site.stellaburgers.test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.stellarburgers.dto.LoginJson;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.equalTo;
import static site.stellarburgers.enums.ErrorMessage.LOGIN_FAIL;

@DisplayName("Логин пользователя")
public class LoginTest extends BaseTest{

    private Response userResponse;
    private Pair<String, LoginJson> pair;

    @Before
    public void generateTestData() {
        pair = generateLoginUser();
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void LoginUserTest() {
        userResponse = authorizationApiSteps.sendUserLogin(pair.getRight());
        step("Проверить, что  статус код 200", () -> {
            userResponse.then().statusCode(200);
        });
        step("Проверить, что тело ответа  содержит [success] со значением true", () -> {
            userResponse.then().assertThat().body("success", equalTo(true));
        });
    }

    @Test
    @DisplayName("Логин с неверной почтой")
    public void LoginUserNotEmailTest() {
        LoginJson loginJson = pair.getRight();
        loginJson.setEmail("");
        userResponse = authorizationApiSteps.sendUserLogin(loginJson);
        step("Проверить, что  статус код 401", () -> {
            userResponse.then().statusCode(401);
        });
        step("Проверить, что  в теле ответа [message] -" + LOGIN_FAIL.getMessage(), () -> {
            userResponse.then().assertThat().body("message", equalTo(LOGIN_FAIL.getMessage()));
        });
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void LoginUserNotPasswordTest() {
        LoginJson loginJson = pair.getRight();
        loginJson.setPassword("");
        userResponse = authorizationApiSteps.sendUserLogin(loginJson);
        step("Проверить, что  статус код 401", () -> {
            userResponse.then().statusCode(401);
        });
        step("Проверить, что  в теле ответа [message] -" + LOGIN_FAIL.getMessage(), () -> {
            userResponse.then().assertThat().body("message", equalTo(LOGIN_FAIL.getMessage()));
        });
    }

    @After
    public void clean() {
        authorizationApiSteps.sendDelete(pair.getLeft());
    }
}
