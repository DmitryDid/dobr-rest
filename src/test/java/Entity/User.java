package Entity;

import Tests.TestBase;
import io.restassured.response.Response;

import java.io.File;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class User extends TestBase {
    String id = "1";
    String name = "Did";
    boolean isMan = true;
    String birthYear = "1986-07-10T12:59:18.99";
    String fileName = "hacker.jpg";
    File image = new File("src/test/java/Resources/" + fileName);
    String latitude = "55.0415000";
    String longitude = "82.9346000";
    String playerId = UUID.randomUUID().toString();

    public Response createUser() {
        Response response = given()
                .spec(multiDataSpec)
                .formParam("id", id)
                .formParam("name", name)
                .formParam("isMan", isMan)
                .formParam("playerId", UUID.randomUUID().toString())
                .formParam("BirthYear", birthYear)
                .formParam("latitude", latitude)
                .formParam("longitude", longitude)
                .multiPart("image", image)
                .when()
                .post("User")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Создан пользователь " + name);
        return response;
    }

    public Response createUser(String uuid) {
        Response response = given()
                .spec(multiDataSpec)
                .formParam("id", id)
                .formParam("name", name)
                .formParam("isMan", isMan)
                .formParam("playerId", uuid)
                .formParam("BirthYear", birthYear)
                .formParam("latitude", latitude)
                .formParam("longitude", longitude)
                .multiPart("image", image)
                .when()
                .post("User")
                .then()
                .extract().response();
        if (response.getStatusCode() != 200) return null;
        System.out.println("Создан пользователь " + name);
        return response;
    }
}
