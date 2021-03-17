import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Chat extends TestBase {

    // /api/v{version}/Chat/company/{companyId}
    @Test
    public void getChatCompanyById() {
        String id = "7";

        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .when()
                .get("Chat/company/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    @Test
    public void getChatUserById() {
        String id = "24";

        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .when()
                .get("Chat/user/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }
}
