package Tests;

import Action.Auth;
import Constants.CONST;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class UserTest extends TestBase {

    // POST /api/v{version}/User
    @Test
    public void postUser() {
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
                .spec(CONST.MULTi_DATA_SPEC)
                //.header("Authorization", "Bearer " + getToken())
                .log().all()
                .formParam("name", name)
                .formParam("isMan", isMan)
                .formParam("playerId", playerId)
                .formParam("BirthYear", birthYear)
                .formParam("latitude", latitude)
                .formParam("longitude", longitude)
                .multiPart("image", image)
                .when()
                .post("User")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);
    }

    // /api/v{version}/User/{id}/image
    @Test
    public void getUserImageById() {
        String id = "14";
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
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
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .log().all()
                .when()
                .get("User")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);
    }

    // PUT /api/v{version}/User/{id}   пересмотреть весь тест
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
                .spec(CONST.MULTi_DATA_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
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

        // GET /api/v{version}/User/{id}
        Response getResponse = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .log().all()
                .when()
                .get("User/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(getResponse);

        JSONObject jsonObject = new JSONObject(response.asString());

        assertEquals("Success", jsonObject.getString("status"));
        assertEquals("bearer", jsonObject.getString("token_type"));
        assertNotNull(jsonObject.getString("access_token"));

        user = jsonObject.getJSONObject("user");

        assertEquals(id, user.get("id"));
        assertEquals("Did", user.get("name"));
        assertEquals(true, user.get("isMan"));
        assertEquals(true, user.get("isMan"));
        assertEquals(55.0415, user.get("latitude"));
        assertEquals(82.9346, user.get("longitude"));
        assertEquals("1986-07-10T12:59:18.99", user.get("birthYear"));
        assertEquals("", user.get("avatarName")); // предположительно бага
        assertEquals("3af5b06b-7e51-4056-9704-976a727de7ee", user.get("playerId")); // предположительно бага


    }

    // /api/v{version}/User/{id}/favorite/{favoriteId}
    @Test
    public void deleteUserFavorite() {
        String userId = "22";
        String favoriteId = "1";
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
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
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
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
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
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
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
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
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .log().uri().log().method()
                .when()
                .delete("User/" + userId + "/favorite/" + favoriteId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(deleteResponse);

        Response putAgainResponse = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
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
