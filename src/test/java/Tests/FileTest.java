package Tests;

import Constants.CONST;
import org.junit.Test;

import static Pages.Auth.getAccessToken;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class FileTest extends TestBase {

    @Test
    public void getFile() {
        int id = 2;

        String response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .when()
                .get("File/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        assertTrue(response.contains("JPEG"));
    }
}
