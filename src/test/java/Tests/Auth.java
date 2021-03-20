package Tests;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class Auth extends TestBase {

    // /api/v{version}/Auth/token/user/{playerId}
    @Test
    public void getTokenForUser() {
        String userid = "aee5c185-29b8-4d4c-a4bf-d7641aefc556";
        Response response = given()
                .spec(baseSpec)
                .log().all()
                .when()
                .post("Auth/token/user/" + userid)
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);

        String token = (String) new JSONObject(response.asString()).get("access_token");

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    // /api/v{version}/Auth/company
    @Test
    public void gettingTokenForCompany() {
        String username = "bagdasarjan.cft@gmail.com"; // email
        String password = "lavash5";                    // password

        Response response = given()
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
        toConsole(response);

        String token = (String) new JSONObject(response.asString()).get("access_token");

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    // /api/v{version}/Auth/token/company/{playerId}
    @Test
    public void getTokenForPlayer() {
        String playerId = "5653c463-de6e-4ab1-b418-3c43814b9e0a";
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
