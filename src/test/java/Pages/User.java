package Pages;

import Constants.CONST;
import DTO.AuthDTO;
import DTO.UserDTO;
import Tests.TestBase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class User extends TestBase {
    static private String id = "1";
    static private String name = "Did";
    static private boolean isMan = true;
    static private String birthYear = "1986-07-10T12:59:18.99";
    static private String fileName = "hacker.jpg";
    static private File image = new File("src/test/java/Resources/" + fileName);
    static private String latitude = "55.0415000";
    static private String longitude = "82.9346000";

    public static AuthDTO createUser() {
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
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
        return response.as(AuthDTO.class);
    }

    public static AuthDTO getRandomUser() {
        Integer countUsers = User.getAllUser().size();
        Integer userId = (int) (Math.random() * (countUsers - 1));
        if (userId <= 0) userId = 1;
        return User.getUser(userId);
    }

    public static ArrayList<UserDTO> getAllUser() {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("User")
                .then()
                .statusCode(200)
                .extract().response();
        return getListUser(response);
    }

    public static ArrayList<UserDTO> getListUser(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = mapper.convertValue(
                response.as(JsonNode.class),
                new TypeReference<List<UserDTO>>() {
                });
        return (ArrayList) list;
    }

    public static AuthDTO getUser(int id) {
        return given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .when()
                .get("User/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response().as(AuthDTO.class);
    }

    public static AuthDTO createUser(String uuid) {
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
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
        return response.as(AuthDTO.class);
    }

    public static UserDTO addedFavoriteForUser(int userId, int favoriteId) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .when()
                .put("User/" + userId + "/favorite/" + favoriteId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.as(UserDTO.class);
    }
}