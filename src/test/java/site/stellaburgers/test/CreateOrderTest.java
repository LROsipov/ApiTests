package site.stellaburgers.test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Test;
import site.stellarburgers.dto.OrderJson;
import site.stellarburgers.dto.UserJson;
import site.stellarburgers.steps.ApiSteps;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.equalTo;
import static site.stellarburgers.enums.ErrorMessage.AUTHORISED_FAIL;
import static site.stellarburgers.enums.ErrorMessage.INGREDIENTS_FAIL;


@DisplayName("Создание заказа")
public class CreateOrderTest extends BaseTest {

    private Response userResponse;
    private Pair<String, UserJson> pair;

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithAuthorisedTest() {
        pair = generateRegistrationUser();
        userResponse = ApiSteps.createOrder(generateOrderData(), pair.getLeft());
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(200);
        });
        step("Проверяем что тело ответа  содержит [success] со значением true", () -> {
            userResponse.then().assertThat().body("success", equalTo(true));
        });
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderNotAuthorisedTest() {
        pair = generateRegistrationUser();
        userResponse = ApiSteps.createOrder(generateOrderData(), "");
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(401);
        });
        step("Проверяем [message] в теле ответ", () -> {
            userResponse.then().assertThat().body("message", equalTo(AUTHORISED_FAIL.getMessage()));
        });
    }

    @Test
    @DisplayName("Создание заказа c ингридиентами")
    public void createOrderWithIngredientsTest() {
        pair = generateRegistrationUser();
        userResponse = ApiSteps.createOrder(generateOrderData(), "");
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(200);
        });
        step("Проверяем что тело ответа  содержит [success] со значением true", () -> {
            userResponse.then().assertThat().body("success", equalTo(true));
        });
    }

    @Test
    @DisplayName("Создание заказа без ингридиентов")
    public void createOrderNotIngredientsTest() {
        pair = generateRegistrationUser();
        List<String> ingredients = new ArrayList<>();
        OrderJson orderJson = new OrderJson(ingredients);
        userResponse = ApiSteps.createOrder(orderJson, pair.getLeft());
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(400);
        });
        step("Проверяем [message] в теле ответ", () -> {
            userResponse.then().assertThat().body("message", equalTo(INGREDIENTS_FAIL.getMessage()));
        });
    }

    @Test
    @DisplayName("Создание заказа с неверным хэшем ингридиентов")
    public void createOrderInvalidHashTest() {
        pair = generateRegistrationUser();
        List<String> ingredients = new ArrayList<>();
        ingredients.add("");
        OrderJson orderJson = new OrderJson(ingredients);
        userResponse = ApiSteps.createOrder(orderJson, pair.getLeft());
        step("Проверяем статус код", () -> {
            userResponse.then().statusCode(500);
        });

    }

    @After
    public void clean() {
        ApiSteps.sendDelete(pair.getLeft());
    }
}
