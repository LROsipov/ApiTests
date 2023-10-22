package site.stellarburgers.enums;

import lombok.AllArgsConstructor;
import site.stellarburgers.utils.Endpoint;

@AllArgsConstructor
public enum StellaBurgersEndpoint implements Endpoint {
    LOGIN("auth/login"),
    USER("auth/user"),
    ORDERS("orders"),
    INGREDIENTS("ingredients"),
    REGISTRATION("auth/register");
    private final String endpoint;

    @Override
    public String getValue() {
        return endpoint;
    }

}
