import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserClient{
    @Step("Создание пользователя")
    public boolean createUser(User user) {
        return given()
//                .header("Content-type", "application/json")
                .spec(User.getRequestSpecification())
                .and()
                .body(user)
                .post(Api.postRegister())
                .prettyPeek()
                .then().statusCode(200)
                .extract()
                .path("success");
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken) {
        if (accessToken == null) {
            return;
        }
        given()
                .header("Authorization", accessToken)
//                .header("Content-type", "application/json")
//                .spec(User.getRequestSpecification())
                .and()
                .when()
                .delete(Api.deleteUser())
                .then()
                .statusCode(202);
    }

    @Step("Логин пользователя")
    public boolean loginUser(User user) {
        return given()
//                .header("Content-type", "application/json")
                .spec(User.getRequestSpecification())
//                .and()
                .body(user)
                .post(Api.postLogin())
                .then()
                .statusCode(200)
                .extract().body().path("success");
    }

    @Step("Создание дубликата пользователя")
    public String createUserDuplicate(User user) {
        return given()
//                .header("Content-type", "application/json")
                .spec(User.getRequestSpecification())
//                .and()
                .body(user)
                .post(Api.postRegister())
                .then().statusCode(403)
                .extract()
                .path("message");
    }

    @Step("Создание пользователя без всех полей")
    public String createUserMissingField(User user) {
        return given()
//                .header("Content-type", "application/json")
                .spec(User.getRequestSpecification())
                .and()
                .body(user)
                .post(Api.postRegister())
                .then().statusCode(403)
                .extract()
                .path("message");
    }

    @Step("Логин с неверными данными")
    public String loginUserWrongField(User user) {
        return given()
//                .header("Content-type", "application/json")
                .spec(User.getRequestSpecification())
                .and()
                .body(user)
                .post(Api.postLogin())
                .then()
                .statusCode(401)
                .extract().body().path("message");
    }

    @Step("Получение токена доступа")
    public String getAccessToken(User user) {
        return given()
//                .header("Content-type", "application/json")
                .spec(User.getRequestSpecification())
                .and()
                .body(user)
                .post(Api.postLogin())
                .then().extract().body().path("accessToken");
    }

    @Step("Изменить данные пользователя")
    public boolean changeUser(User user, String accessToken) {
        Response response = given()
//                .header("Content-type", "application/json")
                .auth()
                .oauth2(accessToken.replace("Bearer ", ""))
                .when()
                .spec(User.getRequestSpecification())
                .body(user)
                .patch(Api.patchUser());
        return response.then().assertThat()
                .statusCode(200)
                .extract().body().path("success");
    }

    @Step("Изменить данные пользователя без авторизации")
    public String changeUserError(User user) {
        Response response = given()
//                .header("Content-type", "application/json")
                .spec(User.getRequestSpecification())
                .body(user)
                .patch(Api.patchUser());
        return response.then().assertThat()
                .statusCode(401)
                .extract().body().path("message");
    }

    @Step("Получить данные о пользователе")
    public String getUser(User user) {
        String accessToken = given()
//                .header("Content-type", "application/json")
                .spec(User.getRequestSpecification())
                .and()
                .body(user)
                .post(Api.postLogin())
                .then().extract().body().path("accessToken");
        Response response = given()
                .auth().oauth2(accessToken.replace("Bearer ", "")).when()
                .get(Api.getUser());
        return response.body().asString();
    }
}