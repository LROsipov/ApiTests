package site.stellaburgers.test;


import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site.stellarburgers.dto.UserJson;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.equalTo;
import static site.stellarburgers.enums.ErrorMessage.CHANGE_EMAIL_FAIL;
import static site.stellarburgers.enums.ErrorMessage.AUTHORISED_FAIL;

@DisplayName("Изменение данных пользователя")
public class ChangingUserDataTest extends BaseTest {
    private Response userResponse;
    private Pair<String, UserJson> pair;

    @BeforeEach
    public void generateTestData() {
        pair = generateRegistrationUser();
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void changeUserNotAuthorisedTest() {
        userResponse = authorizationApiSteps.sendChangeUser(pair.getRight(), "");
        step("Проверить, что  статус код 401", () -> {
            userResponse.then().statusCode(401);
        });
        step("Провить, что в теле ответа сообщение " + AUTHORISED_FAIL.getMessage(), () -> {
            userResponse.then().assertThat().body("message", equalTo(AUTHORISED_FAIL.getMessage()));
        });
    }

    @Test
    @DisplayName("Изменение данных пользователя c авторизацией")
    public void changeUserAuthorisedTest() {
        userResponse = authorizationApiSteps.sendChangeUser(pair.getRight(), pair.getLeft());
        step("Проверить, что  статус код 200", () -> {
            userResponse.then().statusCode(200);
        });
        step("Проверяем что тело ответа  содержит [success] со значением true", () -> {
            userResponse.then().assertThat().body("success", equalTo(true));
        });
    }

    @Test
    @DisplayName("Изменение данных пользователя c авторизацией, используя занятую почту")
    public void changeUserAuthorisedEmailTest() {
        UserJson userJson = pair.getRight();
        userJson.setEmail("test-data@yandex.ru");
        userResponse = authorizationApiSteps.sendChangeUser(userJson, pair.getLeft());
        step("Проверить, что  статус код 403", () -> {
            userResponse.then().statusCode(403);
        });
        step("Провить, что в теле ответа сообщение " + CHANGE_EMAIL_FAIL.getMessage(), () -> {
            userResponse.then().assertThat().body("message", equalTo(CHANGE_EMAIL_FAIL.getMessage()));
        });
    }

    @AfterEach
    public void clean() {
        authorizationApiSteps.sendDelete(pair.getLeft());
    }
}
