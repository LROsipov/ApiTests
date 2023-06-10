package site.stellaburgers.test;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import site.stellarburgers.dto.UserJson;
import site.stellarburgers.factory.RandomUser;
import site.stellarburgers.steps.ApiSteps;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.equalTo;
import static site.stellarburgers.enums.ErrorMessage.INVALID_FIELDS;
import static site.stellarburgers.enums.ErrorMessage.INVALID_USER;

@DisplayName("Создание пользователя")
public class RegistrationTest extends BaseTest {
    ApiSteps apiSteps = new ApiSteps();
    private Response userResponse;

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUserTest() {
        UserJson userJson = RandomUser.getRandomUser();
        userResponse = apiSteps.sendUserRegistration(userJson);
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(200);
        });
        step("Проверяем что тело ответа  содержит [success] со значением true", () -> {
            userResponse.then().assertThat().body("success", equalTo(true));
        });
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createAgainUserTest() {
        UserJson userJson = RandomUser.getRandomUser();
        userResponse = apiSteps.sendUserRegistration(userJson);
        Response againUserResponse = apiSteps.sendUserRegistration(userJson);
        step("Проверяем статус код", () -> {
            againUserResponse.then().statusCode(403);
        });
        step("Проверяем [message] в теле ответ ", () -> {
            againUserResponse.then().assertThat().body("message", equalTo(INVALID_USER.getMessage()));
        });
    }

    @Test
    @DisplayName("Создание пользователя,без пароля")
    public void createUserNotPasswordTest() {
        UserJson userJson = RandomUser.getRandomUser();
        userJson.setPassword("");
        userResponse = apiSteps.sendUserRegistration(userJson);
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(403);
        });
        step("Проверяем [message] в теле ответ ", () -> {
            userResponse.then().assertThat().body("message", equalTo(INVALID_FIELDS.getMessage()));
        });
    }

    @Test
    @DisplayName("Создание пользователя,без имени")
    public void createUserNotNameTest() {
        UserJson userJson = RandomUser.getRandomUser();
        userJson.setName("");
        userResponse = apiSteps.sendUserRegistration(userJson);
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(403);
        });
        step("Проверяем [message] в теле ответ ", () -> {
            userResponse.then().assertThat().body("message", equalTo(INVALID_FIELDS.getMessage()));
        });
    }

    @After
    public void clean() {
        String token = ApiSteps.takeToken(userResponse);
        if (token != null) {
            ApiSteps.sendDelete(token);
        }
    }
}
