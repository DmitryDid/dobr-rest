package Pages;

import Tests.TestBase;

import static io.restassured.RestAssured.given;

public class Registration extends TestBase {

    public static void emailConfirmation(String email) {
        given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .body("{\n" +
                        "  \"email\": \"" + email + "\"\n" +
                        "}")
                .when()
                .post("Registration/email-confirmation")
                .then()
                .statusCode(200);
    }

    public static void emailAcceptance(String email, String code) {
        given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .body("{\n" +
                        "  \"email\": \"" + email + "\",\n" +
                        "  \"code\": \"" + code + "\"\n" +
                        "}")
                .when()
                .post("Registration/email-acceptance")
                .then()
                .statusCode(200);
    }
}
