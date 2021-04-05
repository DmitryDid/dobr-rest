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
import java.util.*;

import static io.restassured.RestAssured.given;

public class User extends TestBase {

    static private String fileName = "hacker.jpg";
    static private File image = new File("src/test/java/Resources/" + fileName);

    public static Map<String, Object> getDefaultUserParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Did");
        map.put("isMan", true);
        map.put("playerId", UUID.randomUUID().toString());
        map.put("birthYear", "1986-07-10T12:59:18.99");
        map.put("latitude", 55.0415000);
        map.put("longitude", 82.9346000);
        return map;
    }

    public static AuthDTO createUser() {
        Map<String, Object> params = getDefaultUserParams();
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .formParams(params)
                .multiPart("image", image)
                .when()
                .post("User")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Создан пользователь " + params.get("name"));
        return response.as(AuthDTO.class);
    }

    public static UserDTO getRandomUser() {
        ArrayList<UserDTO> list = User.getAllUser();
        int countUsers = list.size();
        int index = (int) (Math.random() * (countUsers - 1));
        if (index <= 0) index = 1;
        return list.get(index);
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
        Map<String, Object> params = getDefaultUserParams();
        params.put("playerId", uuid);

        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .formParams(params)
                .multiPart("image", image)
                .when()
                .post("User")
                .then()
                .extract().response();
        if (response.getStatusCode() != 200) return null;
        System.out.println("Создан пользователь " + params.get("uud"));
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