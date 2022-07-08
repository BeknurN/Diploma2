
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChangeUserTest {
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = Api.getBaseURL();
        String email = "3TreesAndBones@yahooo.com";
        String password = "123456";
        String name = "laura";
        user = new User(email, password, name);
    }

    @Test // задание: изменение данных пользователя с авторизацией - email
    @DisplayName("Change email")
    public void changeEmail() {
        user.createUser(user);
        accessToken = user.getAccessToken(user);
        //System.out.println(user.getAccessToken(user));
        user.setEmail("lauraPa.roob@yahoo.com");
        boolean change = user.changeUser(user, accessToken);
        //System.out.println(user.getUser(user));
        //System.out.println(user.getAccessToken(user));
        Assert.assertTrue(change);
    }

    @Test // задание: изменение данных пользователя с авторизацией - password
    @DisplayName("Change password")
    public void changePassword() {
        user.createUser(user);
        accessToken = user.getAccessToken(user);
        //System.out.println(user.getAccessToken(user));
        user.setPassword("j876h()Dke$");
        boolean change = user.changeUser(user, accessToken);
        //System.out.println(user.getUser(user));
        Assert.assertTrue(change);
    }

    @Test // задание: изменение данных пользователя с авторизацией - name
    @DisplayName("Change name")
    public void changeName() {
        user.createUser(user);
        accessToken = user.getAccessToken(user);
        //System.out.println(user.getAccessToken(user));
        user.setName("Laura");
        boolean change = user.changeUser(user, accessToken);
        //System.out.println(user.getUser(user));
        Assert.assertTrue(change);
    }

    @Test // задание: изменение данных пользователя без авторизации
    @DisplayName("Error changing unauthorized user")
    public void changeError() {
        String message = user.changeUserError(user);
        Assert.assertEquals("You should be authorised", message);
    }

    @After
    public void tearDown() {
        user.deleteUser(accessToken);
    }
}