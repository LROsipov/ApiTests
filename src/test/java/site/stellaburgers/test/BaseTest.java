package site.stellaburgers.test;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import site.stellarburgers.dto.LoginJson;
import site.stellarburgers.dto.OrderJson;
import site.stellarburgers.dto.UserJson;
import site.stellarburgers.factory.RandomUser;
import site.stellarburgers.steps.AuthorizationApiSteps;
import site.stellarburgers.steps.OrderApiSteps;

import java.util.List;

import static site.stellarburgers.steps.OrderApiSteps.*;


@Getter
public class BaseTest {
    protected static OrderApiSteps orderApiSteps = new OrderApiSteps();
    protected static AuthorizationApiSteps authorizationApiSteps = new AuthorizationApiSteps();

    @Step("Сгенерировать данные для логина")
    public static Pair<String, LoginJson> generateLoginUser() {
        UserJson userJson = RandomUser.getRandomUser();
        Response response = authorizationApiSteps.sendUserRegistration(userJson);
        return Pair.of(authorizationApiSteps.takeToken(response), LoginJson.from(userJson));
    }

    @Step("Сгенерировать данные для регистрации")
    public static Pair<String, UserJson> generateRegistrationUser() {
        UserJson userJson = RandomUser.getRandomUser();
        Response response = authorizationApiSteps.sendUserRegistration(userJson);
        return Pair.of(authorizationApiSteps.takeToken(response), userJson);
    }

    @Step("Сгенерировать данные для заказа")
    public static OrderJson generateOrderData() {
        return new OrderJson(List.of(takeIdFirstIngredients(getIngredients())));
    }
}
