
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChangeUserTest {
    private User user;
    private String accessToken;
    private UserClient userClient;

    @Before
    public void setUp() {
//        RestAssured.baseURI = Api.getBaseURL();
//        RestAssured.baseURI = String.valueOf(User.getRequestSpecification());
//        RestAssured.baseURI = User.API_URL;
        user = User.getRandom();
        userClient = new UserClient();
    }

    @Test // задание: изменение данных пользователя с авторизацией - email
    @DisplayName("Change email")
    public void changeEmail() {
        userClient.createUser(user);
        accessToken = userClient.getAccessToken(user);
        user.setEmail(User.getRandom().getEmail());
        boolean change = userClient.changeUser(user, accessToken);
        Assert.assertTrue(change);
    }

    @Test // задание: изменение данных пользователя с авторизацией - password
    @DisplayName("Change password")
    public void changePassword() {
        userClient.createUser(user);
        accessToken = userClient.getAccessToken(user);
        user.setPassword(User.getRandom().getPassword());
        boolean change = userClient.changeUser(user, accessToken);
        Assert.assertTrue(change);
    }

    @Test // задание: изменение данных пользователя с авторизацией - name
    @DisplayName("Change name")
    public void changeName() {
        userClient.createUser(user);
        accessToken = userClient.getAccessToken(user);
        user.setName(User.getRandom().getName());
        boolean change = userClient.changeUser(user, accessToken);
        Assert.assertTrue(change);
    }

    @Test // задание: изменение данных пользователя без авторизации
    @DisplayName("Error changing unauthorized user")
    public void changeError() {
        String message = userClient.changeUserError(user);
        Assert.assertEquals("You should be authorised", message);
    }

    @After
    public void tearDown() {
        userClient.deleteUser(accessToken);
    }
}