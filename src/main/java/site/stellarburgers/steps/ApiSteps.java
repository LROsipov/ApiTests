package site.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.stellarburgers.dto.LoginJson;
import site.stellarburgers.dto.UserJson;
import site.stellarburgers.spec.StellarBurgersSpecefication;

import static site.stellarburgers.enums.StellaBurgersEndpoint.*;
import static site.stellarburgers.utils.CoreApiUtils.sendRequest;
import static site.stellarburgers.utils.RequestType.*;

public class ApiSteps {
    @Step("Удаляем пользователя")
    public static void sendDelete(String token) {
        sendRequest(StellarBurgersSpecefication.delete(token), DELETE, USER);
    }

    @Step("Получаем токен")
    public static String takeToken(Response response) {
        return response.then().extract().path("accessToken");
    }

    @Step("Отправялем запрос регистрации пользователя")
    public Response sendUserRegistration(UserJson userJson) {
        return sendRequest(StellarBurgersSpecefication.forStellaBurgers(userJson), POST, REGISTRATION);
    }

    @Step("Отправялем запрос авторизации пользователя")
    public Response sendUserLogin(LoginJson loginJson) {
        return sendRequest(StellarBurgersSpecefication.forLogin(loginJson), POST, LOGIN);
    }

    @Step("Изменяем пользователя")
    public Response sendChangeUser(UserJson userJson, String token) {
        return sendRequest(StellarBurgersSpecefication.changeUser(userJson, token), PATCH, USER);
    }

    @Step("Получаем информацию о заказе")
    public Response sendGetOrder(String token) {
        return sendRequest(StellarBurgersSpecefication.forOrder(token), GET, ORDERS);
    }
}
