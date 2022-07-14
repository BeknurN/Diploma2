import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateOrderTest extends BaseHttpClient{
    UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        super.setUp();
    }
    @Test // задание: создание заказа с авторизацией и ингредиентами
    @DisplayName("Creating order")
    public void createOrderAuth() {
        Faker faker = new Faker();
        User user = User.getRandom();
        boolean response = userClient.createUser(user);
        String accessToken = userClient.getAccessToken(user);
        OrderClient order = new OrderClient();
        boolean burger = order.createOrderAuth(order.burger(), accessToken);
        System.out.println(burger);
        Assert.assertTrue(burger);
        // удаление созданного пользователя
        userClient.deleteUser(accessToken);
    }

    @Test // задание: создание заказа без авторизации, с ингредиентами
    @DisplayName("Creating order")
    public void createOrderNoAuth() {
        OrderClient order = new OrderClient();
        boolean burger = order.createOrderNoAuth(order.burger());
        System.out.println(burger);
        Assert.assertTrue(burger);
    }

    @Test // задание: создание заказа без ингредиентов
    @DisplayName("Creating order")
    public void createOrderNoIngredients() {
        OrderClient order = new OrderClient();
        String message = order.createOrderNoIngredients();
        System.out.println(message);
        Assert.assertEquals("Ingredient ids must be provided", message);
    }

    @Test // задание: создание заказа с неверным хешем ингредиентов
    @DisplayName("Creating order")
    public void createOrderWrongIngredientsHash() {
        OrderClient order = new OrderClient();
        String wrongHash = "wrongHash";
        int expected = 500;
        int actual = order.createOrderWrongHash("{\"ingredients\": [\"" + wrongHash + "\"]}");
        Assert.assertEquals(expected, actual);
    }
}