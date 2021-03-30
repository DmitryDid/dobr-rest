package Pages;

import Constants.CONST;
import DTO.AuthDTO;
import DTO.UserDTO;
import io.restassured.response.Response;
import lombok.Data;

import static io.restassured.RestAssured.given;

@Data
public class Auth {

    public static String accessToken;

    public static String getAccessToken() {
        return accessToken;
    }

    public static Response getRefreshToken(String token) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("Auth/refresh-token")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response;
    }

    public static Response getTokenUserByPlayerId(String id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .when()
                .post("Auth/token/user/" + id)
                .then()
                .extract()
                .response();
        return response;
    }

    public static Response getTokenCompanyByUsernameAndPassword(String username, String password) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .body("{\n" +
                        "  \"username\": \"" + username + "\",\n" +
                        "  \"password\": \"" + password + "\"\n" +
                        "}")
                .when()
                .post("Auth/company")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }

    public static Response getTokenForCompanyByPlayerId(String id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .when()
                .post("Auth/token/company/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response;
    }

    public static Response getCompanyLostPassword(String email, String token) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + token)
                .when()
                .body("{\n" +
                        "  \"email\": \"" + email + "\"\n" +
                        "}")
                .post("Auth/company/lost-password")
                .then()
                .statusCode(200) // 500
                .extract()
                .response();
        System.out.println(response.asString());
        return response;
    }

    public static String getToken() {
        if (accessToken != null) return accessToken;
        Response response = Auth.getTokenUserByPlayerId(CONST.PLAYER_ID);
        if (response.getStatusCode() != 200) {
            String playerId = User.createUser(CONST.PLAYER_ID).as(AuthDTO.class).getUser().getPlayerId();

            return given()
                    .spec(CONST.BASE_SPEC)
                    .when()
                    .post("auth/token/user/" + playerId)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response()
                    .as(AuthDTO.class)
                    .getAccessToken();
        }
        return response.as(AuthDTO.class).getAccessToken();
    }

    public static Response getCompanyRestorePassword(String email, String password, int code) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .body("{\n" +
                        "  \"email\": \"" + email + "\",\n" +
                        "  \"newPassword\": \"" + password + "\",\n" +
                        "  \"code\": \"" + code + "\"\n" +
                        "}")
                .post("Auth/company/restore-password")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }
}
