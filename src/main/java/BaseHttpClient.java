import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;

import static io.restassured.http.ContentType.JSON;

public class BaseHttpClient{
//    @Before
//    public void setUp() {
//        RestAssured.baseURI = API_URL;
//
//    }

    public static final String API_URL = "https://stellarburgers.nomoreparties.site/api/";

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(API_URL)
                .build();
    }

}