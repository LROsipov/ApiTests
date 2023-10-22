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
import static site.stellarburgers.enums.ErrorMessage.AUTHORISED_FAIL;


@DisplayName("Получение заказов конкретного пользователя")
public class OrdersTest extends BaseTest {
    ApiSteps apiSteps = new ApiSteps();
    private Response userResponse;
    private Pair<String, UserJson> pair;

    @Test
    @DisplayName("Получение заказов без авторизации")
    public void getOrdersNotAuthorisedTest() {
        pair = generateRegistrationUser();
        userResponse = apiSteps.sendGetOrder("");
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(401);
        });
        step("Проверяем [message] в теле ответ", () -> {
            userResponse.then().assertThat().body("message", equalTo(AUTHORISED_FAIL.getMessage()));
        });
    }

    @Test
    @DisplayName("Получение заказов авторизированного пользователя")
    public void getOrdersAuthorisedTest() {
        pair = generateRegistrationUser();
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
        ApiSteps.sendDelete(pair.getLeft());
    }
}
