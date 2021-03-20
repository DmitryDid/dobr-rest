package Tests;

import io.restassured.response.Response;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class User extends TestBase {

    // /api/v{version}/User/{id}/image
    @Test
    public void getUserImage() {
        String id = "14";
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .when()
                .get("User/" + id + "/image")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v{version}/User
    @Test
    public void getUsers() {
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
                .multiPart("image", new File("src/test/java/Resources/Kiprensky_Pushkin.jpg"))
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

    // /api/v{version}/User/{id}/favorite/{favoriteId}
    @Test
    public void deleteUserFavorite() {
        String userId = "22";
        String favoriteId = "1";
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .when()
                .delete("User/" + userId + "/favorite/" + favoriteId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v{version}/User/{id}/favorite
    @Test
    public void getUserFavorite() {
        String id = "22";
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .when()
                .get("User/" + id + "/favorite")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v{version}/User/{id}/stories
    @Test
    public void getUserStories() {
        String id = "22";
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .when()
                .get("User/" + id + "/stories")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v{version}/User/{id}/favorite/{favoriteId}
    @Test
    public void putUserFavorite() {
        String id = "22";
        String favoriteId = "3";


        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().uri()
                .when()
                .put("User/" + id + "/favorite/" + favoriteId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }
}
