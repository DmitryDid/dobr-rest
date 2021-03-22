package Tests;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class User extends TestBase {

    // /api/v{version}/User/{id}/image
    @Test
    public void getUserImageById() {
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
    public void getUsersAll() {
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

    // GET /api/v{version}/User/{id}
    @Test
    public void getUserById() {
        int userId = 22;
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .when()
                .get("User/" + userId)
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
        String name = "Did";
        boolean isMan = true;
        String playerId = UUID.randomUUID().toString();
        String birthYear = "1986-07-10T12:59:18.99";
        String fileName = "hacker.jpg";
        File image = new File("src/test/java/Resources/" + fileName);
        String latitude = "55.0415000";
        String longitude = "82.9346000";
        Response response = given()
                .spec(multiDataSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().all()
                .formParam("name", name)
                .formParam("isMan", isMan)
                .formParam("playerId", playerId)
                .formParam("BirthYear", birthYear)
                .formParam("latitude", latitude)
                .formParam("longitude", longitude)
                .multiPart("image", image)
                .when()
                .put("User/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);

        JSONObject postJSON = new JSONObject(response.asString());
        assertEquals(postJSON.getString("status"), "Success");
        JSONObject user = postJSON.getJSONObject("user");

        assertEquals(id, user.get("id").toString());
        assertEquals(name, user.get("name"));
        assertEquals(isMan, user.get("isMan"));
        assertTrue(latitude.contains(user.get("latitude").toString()));
        assertTrue(longitude.contains(user.get("longitude").toString()));
        assertEquals(birthYear, user.get("birthYear"));
        assertEquals(playerId, user.get("playerId"));
        assertTrue(user.get("avatarName").toString().contains(fileName));  // ошибка
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
        String userId = "22";
        String favoriteId = "5";

        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().uri().log().method()
                .when()
                .put("User/" + userId + "/favorite/" + favoriteId)
                .then()
                //.statusCode(200)
                .extract()
                .response();
        toConsole(response);

        assertEquals("Already exist", response.asString());

        Response deleteResponse = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().uri().log().method()
                .when()
                .delete("User/" + userId + "/favorite/" + favoriteId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(deleteResponse);

        Response putAgainResponse = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .log().uri().log().method()
                .when()
                .put("User/" + userId + "/favorite/" + favoriteId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(putAgainResponse);

        JSONObject jsonObject = new JSONObject(putAgainResponse.asString());
        assertEquals(userId, jsonObject.get("id"));
        assertNotNull(jsonObject.get("namej"));
        assertNotNull(jsonObject.get("isMan"));
        assertNotNull(jsonObject.get("latitude"));
        assertNotNull(jsonObject.get("longitude"));
        assertNotNull(jsonObject.get("birthYear"));
        assertNotNull(jsonObject.get("avatarName"));
        assertNotNull(jsonObject.get("playerId"));
    }
}
