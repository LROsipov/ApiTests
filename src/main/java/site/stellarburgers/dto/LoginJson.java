package site.stellarburgers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginJson {
    private String email;
    private String password;

    public static LoginJson from(UserJson userJson) {
        return LoginJson.builder()
                .email(userJson.getEmail())
                .password(userJson.getPassword())
                .build();
    }
}
