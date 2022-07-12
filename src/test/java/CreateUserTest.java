import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateUserTest {
    UserClient userClient;
    @Before
    public void setUp() {
//        RestAssured.baseURI = Api.getBaseURL();
//        RestAssured.baseURI = String.valueOf(User.getRequestSpecification());
//        RestAssured.baseURI = User.API_URL;
        userClient = new UserClient();
    }

    @Test // задание: создать уникального пользователя
    @DisplayName("Creating unique user")
    public void createNewUser() {
        Faker faker = new Faker();
        User user = new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
        boolean created = userClient.createUser(user);
        Assert.assertTrue(created);
        // удаление созданного пользователя
        String accessToken = userClient.getAccessToken(user);
        userClient.deleteUser(accessToken);
    }

    @Test // задание: создать пользователя, который уже зарегистрирован
    @DisplayName("Creating duplicate")
    public void createUserDuplicate() {
        User user = User.getRandom();
        boolean created = userClient.createUser(user);
        Assert.assertTrue(created);
        String message = userClient.createUserDuplicate(user);
        Assert.assertEquals("User already exists", message);
        // удаление созданного пользователя
        String accessToken = userClient.getAccessToken(user);
        userClient.deleteUser(accessToken);
    }

    @Test // задание: создать пользователя и не заполнить одно из обязательных полей - email
    @DisplayName("Creating user without an email")
    public void createUserMissingEmail() {
        User user = User.getRandom();
        user.setEmail("");
        String message = userClient.createUserMissingField(user);
        Assert.assertEquals("Email, password and name are required fields", message);

        // удаление созданного пользователя
        String accessToken = userClient.getAccessToken(user);
        userClient.deleteUser(accessToken);
    }

    @Test // задание: создать пользователя и не заполнить одно из обязательных полей - password
    @DisplayName("Creating user without a password")
    public void createUserMissingPassword() {
        User user = User.getRandom();
        user.setPassword("");
        String message = userClient.createUserMissingField(user);
        Assert.assertEquals("Email, password and name are required fields", message);
        // удаление созданного пользователя
        String accessToken = userClient.getAccessToken(user);
        userClient.deleteUser(accessToken);
    }

    @Test // задание: создать пользователя и не заполнить одно из обязательных полей - name
    @DisplayName("Creating user without a name")
    public void createUserMissingName() {
        User user = User.getRandom();
        user.setName("");
        String message = userClient.createUserMissingField(user);
        Assert.assertEquals("Email, password and name are required fields", message);
        // удаление созданного пользователя
        String accessToken = userClient.getAccessToken(user);
        userClient.deleteUser(accessToken);
    }
}