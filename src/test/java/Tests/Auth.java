package Tests;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class Auth extends TestBase {

    // POST /api/v{version}/Auth/token/user/{playerId}
    @Test
    public void getTokenUserByPlayerId() {
        Response response = given()
                .spec(baseSpec)
                .log().all()
                .when()
                .post("Auth/token/user/" + playerId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);

        String token = (String) new JSONObject(response.asString()).get("access_token");

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    // POST /api/v{version}/Auth/company
    @Test
    public void getCompanyTokenByUsernameAndPassword() {
        Response getResponse = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get("Company")
                .then()
                .statusCode(200)
                .extract().response();

        JSONObject jsonObject = new JSONArray(getResponse.asString()).getJSONObject(0);
        String username = jsonObject.getString("email");
        String password = jsonObject.getString("password");

        Response postResponse = given()
                .spec(baseSpec)
                .body("{\n" +
                        "  \"username\": \"" + username + "\",\n" +
                        "  \"password\": \"" + password + "\"\n" +
                        "}")
                .log().all()
                .when()
                .post("auth/company")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(postResponse);

        String token = (String) new JSONObject(postResponse.asString()).get("access_token");

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    // POST /api/v{version}/Auth/token/company/{playerId}
    @Test
    public void getTokenForCompanyPlayer() {
        Response getResponse = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get("Company")
                .then()
                .statusCode(200)
                .extract().response();

        String playerId = new JSONArray(getResponse.asString()).getJSONObject(0).getString("playerId");

        Response response = given()
                .spec(baseSpec)
                .log().all()
                .when()
                .post("Auth/token/company/" + playerId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);

        String token = (String) new JSONObject(response.asString()).get("access_token");

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    // /api/v{version}/Auth/refresh-token
    @Test
    public void getRefreshToken() {
        String token = getToken();
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("Auth/refresh-token")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);

        String refreshToken = (String) new JSONObject(response.asString()).get("access_token");

        assertEquals(token.length(), refreshToken.length());
        assertNotEquals(token, refreshToken);
    }
}
