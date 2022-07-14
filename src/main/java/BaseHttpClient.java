import io.restassured.RestAssured;
import org.junit.Before;

public class BaseHttpClient {
    @Before
    public void setUp() {
        RestAssured.baseURI = API_URL;

    }

    public static final String API_URL = "https://stellarburgers.nomoreparties.site/api/";

}