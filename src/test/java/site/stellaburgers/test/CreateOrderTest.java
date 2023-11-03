package site.stellaburgers.test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import site.stellarburgers.dto.OrderJson;
import site.stellarburgers.dto.UserJson;
import site.stellarburgers.steps.OrderApiSteps;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.equalTo;
import static site.stellarburgers.enums.ErrorMessage.AUTHORISED_FAIL;
import static site.stellarburgers.enums.ErrorMessage.INGREDIENTS_FAIL;


@DisplayName("Создание заказа")
public class CreateOrderTest extends BaseTest {

    private Response userResponse;
    private Pair<String, UserJson> pair;

    @Before
    public void generateTestData() {
        pair = generateRegistrationUser();
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithAuthorisedTest() {
        userResponse = OrderApiSteps.createOrder(generateOrderData(), pair.getLeft());
        step("Проверить, что  статус код 200", () -> {
            userResponse.then().statusCode(200);
        });
        step("Проверить, что тело ответа  содержит [success] со значением true", () -> {
            userResponse.then().assertThat().body("success", equalTo(true));
        });
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderNotAuthorisedTest() {
        userResponse = OrderApiSteps.createOrder(generateOrderData(), "");
        step("Проверить, что  статус код 401", () -> {
            userResponse.then().statusCode(401);
        });
        step("Проверить, что  в теле ответа [message] -" + AUTHORISED_FAIL.getMessage(), () -> {
            userResponse.then().assertThat().body("message", equalTo(AUTHORISED_FAIL.getMessage()));
        });
    }

    @Test
    @DisplayName("Создание заказа c ингридиентами")
    public void createOrderWithIngredientsTest() {
        userResponse = orderApiSteps.createOrder(generateOrderData(), "");
        step("Проверить, что  статус код 200", () -> {
            userResponse.then().statusCode(200);
        });
        step("Проверить, что тело ответа  содержит [success] со значением true", () -> {
            userResponse.then().assertThat().body("success", equalTo(true));
        });
    }

    @Test
    @DisplayName("Создание заказа без ингридиентов")
    public void createOrderNotIngredientsTest() {
        userResponse = OrderApiSteps.createOrder(new OrderJson(List.of()), pair.getLeft());
        step("Проверить, что  статус код 400", () -> {
            userResponse.then().statusCode(400);
        });
        step("Проверить, что  в теле ответа [message] -" + INGREDIENTS_FAIL.getMessage(), () -> {
            userResponse.then().assertThat().body("message", equalTo(INGREDIENTS_FAIL.getMessage()));
        });
    }

    @Test
    @DisplayName("Создание заказа с неверным хэшем ингридиентов")
    public void createOrderInvalidHashTest() {
        userResponse = OrderApiSteps.createOrder(new OrderJson(List.of("")), pair.getLeft());
        step("Проверить, что  статус код 500", () -> {
            userResponse.then().statusCode(500);
        });

    }

    @AfterEach
    public void clean() {
        authorizationApiSteps.sendDelete(pair.getLeft());
    }
}
