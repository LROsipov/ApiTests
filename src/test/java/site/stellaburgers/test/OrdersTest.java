package site.stellaburgers.test;

import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import site.stellarburgers.dto.UserJson;
import site.stellarburgers.steps.ApiSteps;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.equalTo;
import static site.stellarburgers.enums.ErrorMessage.CHANGE_FAIL;

@DisplayName("Получение заказов конкретного пользователя")
public class OrdersTest extends BaseTest {
    ApiSteps apiSteps = new ApiSteps();
    private Response userResponse;
    private Pair<String, UserJson> pair;

    @Test
    @DisplayName("Получение заказов без авторизации")
    public void getOrdersNotAuthorisedTest() {
        pair = BaseTest.generateRegistrationUser();
        userResponse = apiSteps.sendGetOrder("");
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(401);
        });
        step("Проверяем [message] в теле ответ", () -> {
            userResponse.then().assertThat().body("message", equalTo(CHANGE_FAIL.getMessage()));
        });
    }

    @Test
    @DisplayName("Получение заказов авторизированного пользователя")
    public void getOrdersAuthorisedTest() {
        pair = BaseTest.generateRegistrationUser();
        userResponse = apiSteps.sendGetOrder(pair.getLeft());
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(200);
        });
        step("Проверяем что тело ответа  содержит [success] со значением true", () -> {
            userResponse.then().assertThat().body("success", equalTo(true));
        });
    }

    @After
    public void clean() {
        String token = pair.getLeft();
        ApiSteps.sendDelete(token);
    }
}
