import io.restassured.response.Response;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class User extends TestBase {

    // /api/v{version}/User
    @Test
    public void getUser() {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .when()
                .get("User")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v1/user/5

    @Test
    public void putUserById() {
        String id = "22";

        Response response = given()
                .spec(multiDataSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .multiPart("name", "Dima")
                .multiPart("playerId", "aee5c185-29b8-4d4c-a4bf-d7641aef-did")
                .multiPart("BirthYear", "1986-07-01T00:00:00")
                .multiPart("image", new File("src/test/java/resources/Kiprensky_Pushkin.jpg"))
                .multiPart("Latitude", 0)
                .multiPart("Longitude", 0)
                .when()
                .put("User/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }
}