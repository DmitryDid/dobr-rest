import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Company extends TestBase{

    // /api/v{version}/Company
    @Test
    public void name() {
        Response response = given()
                .spec(baseSpec)
                .log().all()
                .when()
                .get("Company")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }
}
