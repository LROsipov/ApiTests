package site.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.stellarburgers.dto.LoginJson;
import site.stellarburgers.dto.UserJson;
import site.stellarburgers.spec.StellarBurgersSpecification;

import static site.stellarburgers.enums.StellaBurgersEndpoint.*;
import static site.stellarburgers.utils.CoreApiUtils.sendRequest;
import static site.stellarburgers.utils.RequestType.*;

public class AuthorizationApiSteps {
    @Step("Зарегестрировать пользователя")
    public Response sendUserRegistration(UserJson userJson) {
        return sendRequest(StellarBurgersSpecification.forStellaBurgers(userJson), POST, REGISTRATION);
    }

    @Step("Получить токен")
    public static String takeToken(Response response) {
        return response.then().extract().path("accessToken");
    }

    @Step("Авторизировать пользователя")
    public Response sendUserLogin(LoginJson loginJson) {
        return sendRequest(StellarBurgersSpecification.forLogin(loginJson), POST, LOGIN);
    }

    @Step("Удалить пользователя")
    public static void sendDelete(String token) {
        sendRequest(StellarBurgersSpecification.delete(token), DELETE, USER);
    }

    @Step("Изменить данные пользователя")
    public Response sendChangeUser(UserJson userJson, String token) {
        return sendRequest(StellarBurgersSpecification.changeUser(userJson, token), PATCH, USER);
    }
}
