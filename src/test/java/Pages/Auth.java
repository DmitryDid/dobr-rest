package Pages;

import Constants.CONST;
import DTO.AuthDTO;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Auth {

    public static String accessToken;

    public static String getAccessToken() {
        return accessToken;
    }

    public static AuthDTO getRefreshToken(String token) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("Auth/refresh-token")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.as(AuthDTO.class);
    }

    public static AuthDTO getTokenUserByPlayerId(String id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .when()
                .post("Auth/token/user/" + id)
                .then()
                .extract()
                .response();
        return response.as(AuthDTO.class);
    }

    public static AuthDTO getTokenCompanyByUsernameAndPassword(String username, String password) {
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
        return response.as(AuthDTO.class);
    }

    public static AuthDTO getTokenForCompanyByPlayerId(String id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .when()
                .post("Auth/token/company/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.as(AuthDTO.class);
    }

    public static AuthDTO getCompanyLostPassword(String email, String token) {
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
        return response.as(AuthDTO.class);
    }

    public static String getToken() {
        if (accessToken != null) return accessToken;
        try {
            return Auth.getTokenUserByPlayerId(CONST.PLAYER_ID).getAccessToken();
        } catch (Exception e) {
            String playerId = User.createUser(CONST.PLAYER_ID).getUser().getPlayerId();
            return Auth.getTokenUserByPlayerId(playerId).getAccessToken();
        }
    }

    public static AuthDTO getCompanyRestorePassword(String email, String password, int code) {
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
        return response.as(AuthDTO.class);
    }
}
