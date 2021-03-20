package Tests;

import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Chat extends TestBase {

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

    // /api/v{version}/Chat/message
    @Test
    public void getChatMessage() {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .when()
                .get("Chat/message")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v{version}/Chat/message
    @Test
    public void postChatMessage() {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .body("{\n" +
                        "  \"text\": \"testText\",\n" +
                        "  \"companyId\": 5,\n" +
                        "  \"userId\": 24,\n" +
                        "  \"isUserMessage\": true\n" +
                        "}")
                .log().all()
                .when()
                .post("Chat/message")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }
}
