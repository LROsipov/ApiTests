package site.stellarburgers.factory;

import org.apache.commons.lang3.RandomStringUtils;
import site.stellarburgers.dto.UserJson;

public class RandomUser {
    public  static UserJson getRandomUser() {
        return UserJson.builder()
                .name(RandomStringUtils.randomAlphabetic(8))
                .email((RandomStringUtils.randomAlphabetic(8) + "@yandex.ru").toLowerCase())
                .password(RandomStringUtils.randomAlphabetic(8))
                .build();
    }
}
