import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Chat extends TestBase {

    // /api/v{version}/Chat/company/{companyId}
    @Test
    public void name() {
        String companyId = "7";

        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .when()
                .get("Chat/company/" + companyId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }
}
