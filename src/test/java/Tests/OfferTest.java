package Tests;

import Action.Auth;
import Constants.CONST;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class OfferTest extends TestBase {

    // /api/v1/offer/top
    @Test
    public void getOfferTop() {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .log().all()
                .when()
                .get("Offer/top")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v1/offer/55/image
    @Test
    public void getOfferImage() {
        String id = "96";

        Response response = given()
                .spec(CONST.BASE_SPEC)
                .log().all()
                .when()
                .get("Offer/" + id + "/image")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }
}
