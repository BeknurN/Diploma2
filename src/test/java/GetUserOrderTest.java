
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetUserOrderTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = Api.getBaseURL();
    }

    @Test // задание: получение заказов у авторизованного пользователя
    @DisplayName("Get orders of an authorised user")
    public void getAuthOrders() {
        Faker faker = new Faker();
        User user = new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().firstName());
        user.createUser(user);
        String accessToken = user.getAccessToken(user);
        Order order = new Order();
        order.createOrderAuth(order.burger(), accessToken);
        boolean orders = order.getUserOrders(accessToken);
        System.out.println(orders);
        Assert.assertTrue(orders);
        // удаление созданного пользователя
        user.deleteUser(accessToken);
    }

    @Test // задание: получение заказов у неавторизованного пользователя
    @DisplayName("Get orders of a non-authorised user")
    public void getNoAuthOrders() {
        Order order = new Order();
        String message = order.getUserOrdersNoAuth();
        System.out.println(message);
        Assert.assertEquals("You should be authorised", message);
    }
}