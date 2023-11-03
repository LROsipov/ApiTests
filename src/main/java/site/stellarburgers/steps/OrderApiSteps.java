package site.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.stellarburgers.dto.OrderJson;
import site.stellarburgers.spec.StellarBurgersSpecification;

import static site.stellarburgers.enums.StellaBurgersEndpoint.*;
import static site.stellarburgers.utils.CoreApiUtils.sendRequest;
import static site.stellarburgers.utils.RequestType.*;

public class OrderApiSteps {
    @Step("Получить список ингридиентов")
    public static Response getIngredients() {
        return sendRequest(StellarBurgersSpecification.createDefaultSpec().build(), GET, INGREDIENTS);
    }

    @Step("Получить [id] первого ингридиента")
    public static String takeIdFirstIngredients(Response response) {
        return response.then().extract().path("data[0]._id");
    }

    @Step("Создать заказ")
    public static Response createOrder(OrderJson orderJson, String accessToken) {
        return sendRequest(StellarBurgersSpecification.createOrder(orderJson, accessToken), POST, ORDERS);
    }

    @Step("Получить информацию о заказе")
    public Response sendGetOrder(String token) {
        return sendRequest(StellarBurgersSpecification.forOrder(token), GET, ORDERS);
    }
}
