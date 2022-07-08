
import io.qameta.allure.Step;

public class Api {
    @Step("GET BaseURL")
    public static String getBaseURL() {
        return "https://stellarburgers.nomoreparties.site/api/";
    }

    @Step("Получение данных об ингредиентах GET /api/ingredients")
    public static String getIngredients() {
        return "ingredients";
    }

    @Step("Создание заказа POST /api/orders")
    public static String postOrder() {
        return "orders";
    }

    @Step("Восстановление и сброс пароля POST /api/password-reset")
    public static String postResetPassword() {
        return "password-reset";
    }

    @Step("Создание пользователя POST /api/auth/register")
    public static String postRegister() {
        return "auth/register";
    }

    @Step("Авторизация пользователя POST /api/auth/login")
    public static String postLogin() {
        return "auth/login";
    }

    @Step("Выход пользователя POST /api/auth/logout")
    public static String postLogout() {
        return "auth/logout";
    }

    @Step("Обновление токена POST /api/auth/token")
    public static String refreshToken() {
        return "auth/token";
    }

    @Step("Получение информации о пользователе GET /api/auth/user")
    public static String getUser() {
        return "auth/user";
    }

    @Step("Обновление информации о пользователе PATCH /api/auth/user")
    public static String patchUser() {
        return "auth/user";
    }

    @Step("Удаление пользователя DELETE /api/auth/user")
    public static String deleteUser() {
        return "auth/user";
    }

    @Step("Получить все заказы GET /api/orders/all")
    public static String getOrders() {
        return "orders/all";
    }

    @Step("Получить заказы конкретного пользователя GET /api/orders")
    public static String getUserOrders() {
        return "orders";
    }
}