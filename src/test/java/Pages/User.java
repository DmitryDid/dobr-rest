package Pages;

import DTO.AuthDTO;
import DTO.CompanyDTO;
import DTO.CompanyListDTO;
import DTO.UserDTO;
import Tests.TestBase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.Assert;

import java.io.File;
import java.util.*;

import static Pages.Auth.getAccessToken;
import static Pages.Auth.getToken;
import static io.restassured.RestAssured.given;

public class User extends TestBase {

    static public final String FILE_NAME = "hacker.jpg";
    static public final File IMAGE = new File("src/test/java/Resources/" + FILE_NAME);

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
                .spec(MULTI_DATA_SPEC)
                .formParams(params)
                .multiPart("image", IMAGE)
                .when()
                .post("User")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Создан пользователь " + params.get("name"));
        return response.as(AuthDTO.class);
    }

    public static AuthDTO createUser(String uuid) {
        Map<String, Object> params = getDefaultUserParams();
        params.put("playerId", uuid);

        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .formParams(params)
                .multiPart("image", IMAGE)
                .when()
                .post("User")
                .then()
                .extract().response();
        if (response.getStatusCode() != 200) return null;
        System.out.println("Создан пользователь " + params.get("uud"));
        return response.as(AuthDTO.class);
    }

    public static AuthDTO createUser(Map<String, Object> params, File image) {
        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .formParams(params)
                .multiPart("image", image)
                .when()
                .post("User")
                .then()
                .extract().response();
        System.out.println("Создан пользователь с параметрами: " + params.toString());
        return response.as(AuthDTO.class);
    }

    public static AuthDTO updateUser(int id, Map<String, Object> params, File image) {
        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .formParams(params)
                .multiPart("image", image)
                .when()
                .put("User/" + id)
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Обновлен пользователь с id: " + id);
        return response.as(AuthDTO.class);
    }

    public static UserDTO getRandomUser() {
        ArrayList<UserDTO> list = User.getAllUser();
        int countUsers = list.size();
        int index = (int) (Math.random() * (countUsers - 1));
        if (index <= 0) index = 0;
        return list.get(index);
    }

    public static ArrayList<UserDTO> getAllUser() {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
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
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get("User/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response().as(AuthDTO.class);
    }

    public static void addedFavoriteForUser(int userId, int companyId) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getToken())
                .when()
                .put("User/" + userId + "/favorite/" + companyId);
        toConsole(response);
        Assert.assertEquals(200, response.getStatusCode());
        System.out.printf("Добавлена связка пользователя %s и компании %s%n", userId, companyId);
    }

    public static ArrayList<CompanyDTO> getUserFavorite(int id) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get("User/" + id + "/favorite")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return Company.getListCompany(response);
    }

    public static void deleteFavorite(int userId, int companyId) {
        given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getToken())
                .when()
                .delete("User/" + userId + "/favorite/" + companyId)
                .then()
                .statusCode(200);
        System.out.printf("Удалена связка пользователя %s и компании %s%n", userId, companyId);
    }

    public static ArrayList<CompanyListDTO> getStories(int id) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .when()
                .get("User/" + id + "/stories")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return Company.getListStories(response);
    }
}