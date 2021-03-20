package Tests;

import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Registration extends TestBase {

    // /api/v{version}/Registration/email-acceptance
    @Test
    public void emailAcceptance() {
        String email = "email@mail.ru";
        String code = "1234";
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
        String email = "email@mail.ru";
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
