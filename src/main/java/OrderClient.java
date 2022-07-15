import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

public class OrderClient extends BaseHttpClient{
    String bun;
    String sauce;
    String meat;
    String cheese;



    @Step("Авторизованный пользователь создаёт заказ")
    public boolean createOrderAuth(String burgerIngredients, String accessToken) {
        Response response = given()
                .spec(getRequestSpecification())
                .auth().oauth2(accessToken.replace("Bearer ", "")).when()
                .body(burgerIngredients)
                .post(Api.postOrder());
        return response.then().assertThat()
                .statusCode(200)
                .extract().body().path("success");
    }


    public String burger() {
        var map = new HashMap<String, Object>();

        var ingredients = new String[]{bun, sauce, meat, cheese};
        map.put("ingredients", ingredients);
        return map.toString();

    }

    @Step("Неавторизованный пользователь создаёт заказ")
    public boolean createOrderNoAuth(String burgerIngredients) {
        Response response = given()
                .spec(getRequestSpecification())
                .body(burgerIngredients)
                .post(Api.postOrder());
        return response.then().assertThat()
                .statusCode(200)
                .extract().body().path("success");
    }

    @Step("Создание заказа без ингредиентов")
    public String createOrderNoIngredients() {
        Response response = given()
                .spec(getRequestSpecification())
                .post(Api.postOrder());
        return response.then().assertThat()
                .statusCode(400)
                .extract().body().path("message");
    }

    @Step("В заказе указаны ингредиенты с неверным хешем")
    public int createOrderWrongHash(String wrongHash) {
        Response response = given()
                .spec(getRequestSpecification())
                .body(wrongHash)
                .post(Api.postOrder());
        return response.statusCode();
    }

    @Step("Получить заказы авторизованного пользователя")
    public boolean getUserOrders(String accessToken) {
        Response response = given()
                .spec(getRequestSpecification())
                .auth().oauth2(accessToken.replace("Bearer ", "")).when()
                .get(Api.getUserOrders());
        return response.then().assertThat()
                .statusCode(200)
                .extract().body().path("success");
    }

    @Step("Получить заказы неавторизованного пользователя")
    public String getUserOrdersNoAuth() {
        Response response = given()
                .spec(getRequestSpecification())
                .get(Api.getUserOrders());
        return response.then().assertThat()
                .statusCode(401)
                .extract().body().path("message");
    }
}