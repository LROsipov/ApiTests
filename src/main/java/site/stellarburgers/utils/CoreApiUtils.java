package site.stellarburgers.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CoreApiUtils {
    public static Response sendRequest(RequestSpecification requestSpecification, RequestType requestType, Endpoint endpoint) {
        return (Response) RestAssured.given(requestSpecification).request(requestType.name(), endpoint.getValue(), new Object[0]);
    }
}
