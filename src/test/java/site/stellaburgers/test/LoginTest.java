package site.stellaburgers.test;

import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import site.stellarburgers.dto.LoginJson;
import site.stellarburgers.steps.ApiSteps;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.equalTo;
import static site.stellaburgers.test.BaseTest.generateLoginUser;
import static site.stellarburgers.enums.ErrorMessage.LOGIN_FAIL;

@DisplayName("Логин пользователя")
public class LoginTest {
    ApiSteps apiSteps = new ApiSteps();
    private Response userResponse;
    private Pair<String, LoginJson> pair;

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void LoginUserTest() {
        pair = generateLoginUser();
        userResponse = apiSteps.sendUserLogin(pair.getRight());
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(200);
        });
        step("Проверяем что тело ответа  содержит [success] со значением true", () -> {
            userResponse.then().assertThat().body("success", equalTo(true));
        });
    }

    @Test
    @DisplayName("Логин с неверным логином")
    public void LoginUserNotEmailTest() {
        pair = generateLoginUser();
        LoginJson loginJson = pair.getRight();
        loginJson.setEmail("");
        userResponse = apiSteps.sendUserLogin(loginJson);
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(401);
        });
        step("Проверяем [message] в теле ответ", () -> {
            userResponse.then().assertThat().body("message", equalTo(LOGIN_FAIL.getMessage()));
        });
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void LoginUserNotPasswordTest() {
        pair = generateLoginUser();
        LoginJson loginJson = pair.getRight();
        loginJson.setPassword("");
        userResponse = apiSteps.sendUserLogin(loginJson);

        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(401);
        });
        step("Проверяем [message] в теле ответ", () -> {
            userResponse.then().assertThat().body("message", equalTo(LOGIN_FAIL.getMessage()));
        });
    }

    @After
    public void clean() {
        String token = pair.getLeft();
        ApiSteps.sendDelete(token);
    }
}
