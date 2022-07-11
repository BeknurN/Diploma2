import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {
    UserClient userClient;
    @Before
    public void setUp() {
        RestAssured.baseURI = Api.getBaseURL();
        userClient = new UserClient();
    }

    @Test // задание: логин под существующим пользователем
    @DisplayName("Real User Login")
    public void realUserLogin() {
        User user = new User("laura2022.roob@yahoo.com", "123456", "laura");
        userClient.createUser(user);
        boolean login = userClient.loginUser(user);
        Assert.assertTrue(login);
        // удаление созданного пользователя
        String accessToken = userClient.getAccessToken(user);
        userClient.deleteUser(accessToken);
    }

    @Test // задание: логин с неверным логином и паролем
    @DisplayName("Not Real User Login")
    public void notRealUserLogin() {
        Faker faker = new Faker();
        User user = new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
        String message = userClient.loginUserWrongField(user);
        Assert.assertEquals("email or password are incorrect", message);
    }
}