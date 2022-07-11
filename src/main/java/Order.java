
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class Order {
    String bun = "61c0c5a71d1f82001bdaaa6c";
    String sauce = "61c0c5a71d1f82001bdaaa72";
    String meat = "61c0c5a71d1f82001bdaaa6e";
    String cheese = "61c0c5a71d1f82001bdaaa7a";

    public Order() {
    }

    @Step("Авторизованный пользователь создаёт заказ")
    public boolean createOrderAuth(String burgerIngredients, String accessToken) {
        Response response = given()
                .header("Content-type", "application/json")
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
//        return "{\"ingredients\": [\"" + bun + "\",\"" + sauce + "\",\"" + meat + "\",\"" + cheese + "\"]}";
    }

    @Step("Неавторизованный пользователь создаёт заказ")
    public boolean createOrderNoAuth(String burgerIngredients) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(burgerIngredients)
                .post(Api.postOrder());
        return response.then().assertThat()
                .statusCode(200)
                .extract().body().path("success");
    }

    @Step("Создание заказа без ингредиентов")
    public String createOrderNoIngredients() {
        Response response = given()
                .header("Content-type", "application/json")
                .post(Api.postOrder());
        return response.then().assertThat()
                .statusCode(400)
                .extract().body().path("message");
    }

    @Step("В заказе указаны ингредиенты с неверным хешем")
    public int createOrderWrongHash(String wrongHash) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(wrongHash)
                .post(Api.postOrder());
        return response.statusCode();
    }

    @Step("Получить заказы авторизованного пользователя")
    public boolean getUserOrders(String accessToken) {
        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken.replace("Bearer ", "")).when()
                .get(Api.getUserOrders());
        return response.then().assertThat()
                .statusCode(200)
                .extract().body().path("success");
    }

    @Step("Получить заказы неавторизованного пользователя")
    public String getUserOrdersNoAuth() {
        Response response = given()
                .header("Content-type", "application/json")
                .get(Api.getUserOrders());
        return response.then().assertThat()
                .statusCode(401)
                .extract().body().path("message");
    }
}