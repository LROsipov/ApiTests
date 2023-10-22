package site.stellarburgers.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorMessage {
    INGREDIENTS_FAIL("Ingredient ids must be provided"),
    CHANGE_EMAIL_FAIL("User with such email already exists"),
    AUTHORISED_FAIL("You should be authorised"),
    LOGIN_FAIL("email or password are incorrect"),
    INVALID_USER("User already exists"),
    INVALID_FIELDS("Email, password and name are required fields");

    private String message;
}


