package site.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.stellarburgers.dto.LoginJson;
import site.stellarburgers.dto.OrderJson;
import site.stellarburgers.dto.UserJson;
import site.stellarburgers.spec.StellarBurgersSpecification;

import static site.stellarburgers.enums.StellaBurgersEndpoint.*;
import static site.stellarburgers.utils.CoreApiUtils.sendRequest;
import static site.stellarburgers.utils.RequestType.*;

public class ApiSteps {
    @Step("Удаляем пользователя")
    public static void sendDelete(String token) {
        sendRequest(StellarBurgersSpecification.delete(token), DELETE, USER);
    }

    @Step("Получаем токен")
    public static String takeToken(Response response) {
        return response.then().extract().path("accessToken");
    }

    @Step("Получаем список ингридиентов")
    public static Response getIngredients() {
        return sendRequest(StellarBurgersSpecification.createDefaultSpec().build(), GET, INGREDIENTS);
    }

    @Step("Получаем [id] ингридиента")
    public static String takeIdIngredients(Response response) {
        return response.then().extract().path("data[0]._id");
    }

    @Step("Создаем заказ")
    public static Response createOrder(OrderJson orderJson, String accessToken) {
        return sendRequest(StellarBurgersSpecification.createOrder(orderJson, accessToken), POST, ORDERS);
    }

    @Step("Отправялем запрос регистрации пользователя")
    public Response sendUserRegistration(UserJson userJson) {
        return sendRequest(StellarBurgersSpecification.forStellaBurgers(userJson), POST, REGISTRATION);
    }

    @Step("Отправялем запрос авторизации пользователя")
    public Response sendUserLogin(LoginJson loginJson) {
        return sendRequest(StellarBurgersSpecification.forLogin(loginJson), POST, LOGIN);
    }

    @Step("Изменяем пользователя")
    public Response sendChangeUser(UserJson userJson, String token) {
        return sendRequest(StellarBurgersSpecification.changeUser(userJson, token), PATCH, USER);
    }

    @Step("Получаем информацию о заказе")
    public Response sendGetOrder(String token) {
        return sendRequest(StellarBurgersSpecification.forOrder(token), GET, ORDERS);
    }
}
