package Tests;

import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class FiasTest extends TestBase {

    // /api/v{version}/Fias/address/{text}
    @Test
    public void getFiasAddressText() {
        String text = "test_address";
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + auth.getToken())
                .log().all()
                .when()
                .get("Fias/address/" + text)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);
    }

    // /api/v{version}/Fias/inn/{inn}
    @Test
    public void getFiasInn() {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + auth.getToken())
                .log().all()
                .when()
                .get("Fias/inn/" + "6240283167")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);
    }
}
