package Action;

import Constants.CONST;
import DTO.AuthDTO;
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
                .statusCode(200)
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
        return response;
    }

    public static String getToken() {
        if (accessToken != null) return accessToken;
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .when()
                .post("auth/token/user/" + CONST.PLAYER_ID)
                .then()
                .extract().response();

        if (response.getStatusCode() != 200) {

            User.createUser(CONST.PLAYER_ID);

            response = given()
                    .spec(CONST.BASE_SPEC)
                    .when()
                    .post("auth/token/user/")
                    .then()
                    .statusCode(200)
                    .extract().response();
            accessToken = response.as(AuthDTO.class).getAccessToken();
            return accessToken;
        }
        accessToken = response.as(AuthDTO.class).getAccessToken();
        return accessToken;
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
