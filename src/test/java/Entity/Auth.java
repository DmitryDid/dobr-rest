package Entity;

import Constants.Const;
import Tests.TestBase;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class Auth extends TestBase {

    public static String accessToken;

    public Response getRefreshToken(String token) {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("Auth/refresh-token")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response;
    }

    public Response getTokenUserByPlayerId(String id) {
        Response response = given()
                .spec(baseSpec)
                .when()
                .post("Auth/token/user/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response;
    }

    public Response getTokenCompanyByUsernameAndPassword(String username, String password) {
        Response response = given()
                .spec(baseSpec)
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

    public Response getTokenForCompanyByPlayerId(String id) {
        Response response = given()
                .spec(baseSpec)
                .when()
                .post("Auth/token/company/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response;
    }

    public Response getCompanyLostPassword(String email, String token) {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .body("{\n" +
                        "  \"email\": \"" + email + "\"\n" +
                        "}")
                .post("Auth/company/lost-password")
                .then()
                .statusCode(200) // 500
                .extract()
                .response();
        toConsole(response);
        return response;
    }

    public String getToken() {
        if (accessToken != null) return accessToken;
        Response response = given()
                .spec(baseSpec)
                .when()
                .post("auth/token/user/" + Const.PLAYER_ID)
                .then()
                .extract().response();

        if (response.getStatusCode() != 200) {

            user.createUser(Const.PLAYER_ID);

            response = given()
                    .spec(baseSpec)
                    .when()
                    .post("auth/token/user/")
                    .then()
                    .statusCode(200)
                    .extract().response();
            accessToken = (String) new JSONObject(response.body().asString()).get("access_token");
            return accessToken;
        }
        accessToken = (String) new JSONObject(response.body().asString()).get("access_token");
        return accessToken;
    }
}
