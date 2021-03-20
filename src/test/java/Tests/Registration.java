package Tests;

import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Registration extends TestBase {

    String email = "nsk_dem@mail.ru";
    String code = "3733"; // придет на почту.

    // /api/v{version}/Registration/email-acceptance
    @Test
    public void emailAcceptance() {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .body("{\n" +
                        "  \"email\": \"" + email + "\",\n" +
                        "  \"code\": \"" + code + "\"\n" +
                        "}")
                .log().all()
                .when()
                .post("Registration/email-acceptance")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v{version}/Registration/email-confirmation
    @Test
    public void emailConfirmation() {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .body("{\n" +
                        "  \"email\": \"" + email + "\"\n" +
                        "}")
                .log().all()
                .when()
                .post("Registration/email-confirmation")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }
}
