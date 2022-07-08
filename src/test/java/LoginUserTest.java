import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static jdk.javadoc.doclet.DocletEnvironment.ModuleMode.API;

public class LoginUserTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = Api.getBaseURL();
    }

    @Test // задание: логин под существующим пользователем
    @DisplayName("Real User Login")
    public void realUserLogin() {
        User user = new User("laura2022.roob@yahoo.com", "123456", "laura");
        user.createUser(user);
        boolean login = user.loginUser(user);
        Assert.assertTrue(login);
        // удаление созданного пользователя
        String accessToken = user.getAccessToken(user);
        user.deleteUser(accessToken);
    }

    @Test // задание: логин с неверным логином и паролем
    @DisplayName("Not Real User Login")
    public void notRealUserLogin() {
        Faker faker = new Faker();
        User user = new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
        String message = user.loginUserWrongField(user);
        Assert.assertEquals("email or password are incorrect", message);
    }
}