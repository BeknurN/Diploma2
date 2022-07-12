import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetUserOrderTest {
    UserClient userClient;
    @Before
    public void setUp() {
//        RestAssured.baseURI = Api.getBaseURL();
//        RestAssured.baseURI = User.API_URL;
        userClient = new UserClient();
    }

    @Test // задание: получение заказов у авторизованного пользователя
    @DisplayName("Get orders of an authorised user")
    public void getAuthOrders() {
        Faker faker = new Faker();
        User user = new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
        userClient.createUser(user);
        String accessToken = userClient.getAccessToken(user);
        OrderClient order = new OrderClient();
        order.createOrderAuth(order.burger(), accessToken);
        boolean orders = order.getUserOrders(accessToken);
        System.out.println(orders);
        Assert.assertTrue(orders);
        // удаление созданного пользователя
        userClient.deleteUser(accessToken);
    }

    @Test // задание: получение заказов у неавторизованного пользователя
    @DisplayName("Get orders of a non-authorised user")
    public void getNoAuthOrders() {
        OrderClient order = new OrderClient();
        String message = order.getUserOrdersNoAuth();
        System.out.println(message);
        Assert.assertEquals("You should be authorised", message);
    }
}