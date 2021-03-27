package Tests;

import Constants.Const;
import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class RegistrationTest extends TestBase {

    String code = "3733"; // придет на почту.

    // /api/v{version}/Registration/email-acceptance
    @Ignore // подтверждение приходит на почту
    @Test
    public void emailAcceptance() {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + auth.getToken())
                .body("{\n" +
                        "  \"email\": \"" + Const.EMAIL + "\",\n" +
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
                .header("Authorization", "Bearer " + auth.getToken())
                .body("{\n" +
                        "  \"email\": \"" + Const.EMAIL + "\"\n" +
                        "}")
                .log().all()
                .when()
                .post("Registration/email-confirmation")
                .then()
                //.statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }
}
