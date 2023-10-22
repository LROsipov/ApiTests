package site.stellaburgers.test;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import site.stellarburgers.dto.LoginJson;
import site.stellarburgers.dto.OrderJson;
import site.stellarburgers.dto.UserJson;
import site.stellarburgers.factory.RandomUser;
import site.stellarburgers.steps.ApiSteps;

import java.util.ArrayList;
import java.util.List;

import static site.stellarburgers.steps.ApiSteps.getIngredients;
import static site.stellarburgers.steps.ApiSteps.takeIdIngredients;


@Getter
public class BaseTest {
    protected static ApiSteps apiSteps;

    @Step("Генерируем данные для логина")
    public static Pair<String, LoginJson> generateLoginUser() {
        UserJson userJson = RandomUser.getRandomUser();
        apiSteps = new ApiSteps();
        Response response = apiSteps.sendUserRegistration(userJson);
        LoginJson loginJson = LoginJson.from(userJson);
        String token = ApiSteps.takeToken(response);
        Pair<String, LoginJson> pair = Pair.of(token, loginJson);
        return pair;
    }

    @Step("Генерируем данные для регистрации")
    public static Pair<String, UserJson> generateRegistrationUser() {
        apiSteps = new ApiSteps();
        UserJson userJson = RandomUser.getRandomUser();
        Response response = apiSteps.sendUserRegistration(userJson);
        String token = ApiSteps.takeToken(response);
        Pair<String, UserJson> pair = Pair.of(token, userJson);
        return pair;
    }

    @Step("Генерируем данные для заказа")
    public static OrderJson generateOrderData() {
        apiSteps = new ApiSteps();
        List<String> ingredients = new ArrayList<>();
        ingredients.add(takeIdIngredients(getIngredients()));
        OrderJson orderJson = new OrderJson(ingredients);
        return orderJson;
    }
}
