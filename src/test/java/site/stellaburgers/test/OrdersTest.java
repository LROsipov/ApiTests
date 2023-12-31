package site.stellaburgers.test;

import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import site.stellarburgers.dto.UserJson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.equalTo;
import static site.stellarburgers.enums.ErrorMessage.AUTHORISED_FAIL;


@DisplayName("Получение заказов конкретного пользователя")
public class OrdersTest extends BaseTest {
    private Response userResponse;
    private Pair<String, UserJson> pair;
    @BeforeEach
    public void generateTestData() {
        pair = generateRegistrationUser();
    }

    @Test
    @DisplayName("Получение заказов без авторизации")
    public void getOrdersNotAuthorisedTest() {
        userResponse = orderApiSteps.sendGetOrder("");
        step("Проверить, что  статус код 401", () -> {
            userResponse.then().statusCode(401);
        });
        step("Проверить, что  в теле ответа [message] -" + AUTHORISED_FAIL.getMessage(), () -> {
            userResponse.then().assertThat().body("message", equalTo(AUTHORISED_FAIL.getMessage()));
        });
    }

    @Test
    @DisplayName("Получение заказов авторизированного пользователя")
    public void getOrdersAuthorisedTest() {
        userResponse = orderApiSteps.sendGetOrder(pair.getLeft());
        step("Проверить, что  статус код 200", () -> {
            userResponse.then().statusCode(200);
        });
        step("Проверить, что тело ответа  содержит [success] со значением true", () -> {
            userResponse.then().assertThat().body("success", equalTo(true));
        });
    }

    @AfterEach
    public void clean() {
        authorizationApiSteps.sendDelete(pair.getLeft());
    }
}
