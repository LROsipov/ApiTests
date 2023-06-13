package site.stellaburgers.test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Test;
import site.stellarburgers.dto.UserJson;
import site.stellarburgers.steps.ApiSteps;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.equalTo;
import static site.stellarburgers.enums.ErrorMessage.CHANGE_EMAIL_FAIL;
import static site.stellarburgers.enums.ErrorMessage.AUTHORISED_FAIL;

@DisplayName("Изменение данных пользователя")
public class ChangingUserDataTest extends BaseTest {
    ApiSteps apiSteps = new ApiSteps();
    private Response userResponse;
    private Pair<String, UserJson> pair;

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void changeUserNotAuthorisedTest() {
        pair = generateRegistrationUser();
        userResponse = apiSteps.sendChangeUser(pair.getRight(), "");
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(401);
        });
        step("Проверяем [message] в теле ответ", () -> {
            userResponse.then().assertThat().body("message", equalTo(AUTHORISED_FAIL.getMessage()));
        });
    }

    @Test
    @DisplayName("Изменение данных пользователя c авторизацией")
    public void changeUserAuthorisedTest() {
        pair = generateRegistrationUser();
        userResponse = apiSteps.sendChangeUser(pair.getRight(), pair.getLeft());
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(200);
        });
        step("Проверяем что тело ответа  содержит [success] со значением true", () -> {
            userResponse.then().assertThat().body("success", equalTo(true));
        });
    }

    @Test
    @DisplayName("Изменение данных пользователя c авторизацией, используя занятую почту")
    public void changeUserAuthorisedEmailTest() {
        pair = generateRegistrationUser();
        UserJson userJson = pair.getRight();
        userJson.setEmail("test-data@yandex.ru");
        userResponse = apiSteps.sendChangeUser(userJson, pair.getLeft());
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(403);
        });
        step("Проверяем [message] в теле ответ", () -> {
            userResponse.then().assertThat().body("message", equalTo(CHANGE_EMAIL_FAIL.getMessage()));
        });
    }

    @After
    public void clean() {
        ApiSteps.sendDelete(pair.getLeft());
    }
}
