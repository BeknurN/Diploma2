
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateUserTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = Api.getBaseURL();
    }

    @Test // задание: создать уникального пользователя
    @DisplayName("Creating unique user")
    public void createNewUser() {
        Faker faker = new Faker();
        User user = new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
        boolean created = user.createUser(user);
        Assert.assertTrue(created);
        // удаление созданного пользователя
        String accessToken = user.getAccessToken(user);
        user.deleteUser(accessToken);
    }

    @Test // задание: создать пользователя, который уже зарегистрирован
    @DisplayName("Creating duplicate")
    public void createUserDuplicate() {
        User user = new User("laura.roob@yahoo.com", "123456", "laura");
        boolean created = user.createUser(user);
        Assert.assertTrue(created);
        String message = user.createUserDuplicate(user);
        Assert.assertEquals("User already exists", message);
        // удаление созданного пользователя
        String accessToken = user.getAccessToken(user);
        user.deleteUser(accessToken);
    }

    @Test // задание: создать пользователя и не заполнить одно из обязательных полей - email
    @DisplayName("Creating user without an email")
    public void createUserMissingEmail() {
        User user = new User("", "123456", "laura");
        String message = user.createUserMissingField(user);
        Assert.assertEquals("Email, password and name are required fields", message);
    }

    @Test // задание: создать пользователя и не заполнить одно из обязательных полей - password
    @DisplayName("Creating user without a password")
    public void createUserMissingPassword() {
        User user = new User("laura.roob@yahoo.com", "", "laura");
        String message = user.createUserMissingField(user);
        Assert.assertEquals("Email, password and name are required fields", message);
    }

    @Test // задание: создать пользователя и не заполнить одно из обязательных полей - name
    @DisplayName("Creating user without a name")
    public void createUserMissingName() {
        User user = new User("laura.roob@yahoo.com", "123456", "");
        String message = user.createUserMissingField(user);
        Assert.assertEquals("Email, password and name are required fields", message);
    }
}