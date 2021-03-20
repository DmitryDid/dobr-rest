package Tests;

import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Fias extends TestBase {

    // /api/v{version}/Fias/address/{text}
    @Test
    public void getFiasAddressText() {
        String text = "text";
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
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
        String inn = "text";
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .when()
                .get("Fias/inn/" + inn)
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }
}
