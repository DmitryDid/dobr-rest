package Tests;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class ChatTest extends TestBase {

    // GET /api/v{version}/Chat/user/{userId}
    @Test
    public void getChatUserById() {
        String userId = "15";
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + auth.getToken())
                .log().uri().log().method()
                .when()
                .get("Chat/user/" + userId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);

        assertTrue(((Integer) new JSONObject(response.asString()).get("lastId")) > 0);

        JSONObject jsonObject = new JSONObject(response.asString());
        JSONArray chats = jsonObject.getJSONArray("chats");

        assertTrue(chats.length() > 0);
        for (int i = 0; i < chats.length(); i++) {
            JSONObject company = chats.getJSONObject(i).getJSONObject("company");
            assertNotNull(company.get("id"));
            assertNotNull(company.get("latitude"));
            assertNotNull(company.get("longitude"));
            assertNotNull(company.get("nameOfficial"));
            assertNotNull(company.get("name"));
            assertNotNull(company.get("representative"));
            assertNotNull(company.get("phone"));
            assertNotNull(company.get("email"));
            assertNotNull(company.get("inn"));
            assertNotNull(company.get("password"));
            assertNotNull(company.get("address"));
            assertNotNull(company.get("timeOfWork"));
            assertNotNull(company.get("emailConfirmed"));
            assertNotNull(company.get("playerId"));
            assertNotNull(company.get("playerId"));
            assertNotNull(company.get("productCategory"));
            assertNotNull(company.get("avatarName"));

            String lastMessage = (String) chats.getJSONObject(i).get("lastMessage");
            String dateTime = (String) chats.getJSONObject(i).get("dateTime");

            assertTrue(lastMessage.length() > 0);
            assertTrue(dateTime.length() > 0);

            System.out.println(lastMessage);
        }
    }

    // GET /api/v{version}/Chat/company/{companyId}
    @Test
    public void getChatCompanyById() {
        String companyId = "5";
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + auth.getToken())
                .log().uri().log().method()
                .when()
                .get("Chat/company/" + companyId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);

        assertTrue(((Integer) new JSONObject(response.asString()).get("lastId")) > 0);

        JSONObject jsonObject = new JSONObject(response.asString());
        JSONArray chats = jsonObject.getJSONArray("chats");

        for (int i = 0; i < chats.length(); i++) {
            JSONObject users = chats.getJSONObject(i).getJSONObject("user");
            assertNotNull(users.get("id"));
            assertNotNull(users.get("name"));
            assertNotNull(users.get("isMan"));
            assertNotNull(users.get("latitude"));
            assertNotNull(users.get("longitude"));
            assertNotNull(users.get("birthYear"));
            assertNotNull(users.get("avatarName"));
            assertNotNull(users.get("playerId"));

            String lastMessage = (String) chats.getJSONObject(i).get("lastMessage");
            String dateTime = (String) chats.getJSONObject(i).get("dateTime");

            assertTrue(lastMessage.length() > 0);
            assertTrue(dateTime.length() > 0);

            System.out.println(lastMessage);
        }
    }

    // не работает
    // GET /api/v{version}/Chat/message
    @Test
    public void getChatMessage() {
        String token = auth.getToken();
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("Chat/message")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);
    }

    // /api/v{version}/Chat/message
    @Test
    public void postChatMessage() {
        String text = "testText" + getUniqueNumber(4);
        int companyId = 5;
        int userId = 15;
        boolean isUserMessage = true;
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + auth.getToken())
                .body("{\n" +
                        "  \"text\": \"" + text + "\",\n" +
                        "  \"companyId\": " + companyId + ",\n" +
                        "  \"userId\": " + userId + ",\n" +
                        "  \"isUserMessage\": " + isUserMessage + "\n" +
                        "}")
                .log().all()
                .when()
                .post("Chat/message")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);

        JSONObject jsonObject = new JSONObject(response.asString());
        Integer id = (Integer) jsonObject.get("id");
        assertTrue(id > 0);

        Date sendingTime = getDate((String) jsonObject.get("sendingTime"));
        assertEquals(sendingTime.getYear(), new Date().getYear());
        assertEquals(sendingTime.getMonth(), new Date().getMonth());
        assertEquals(sendingTime.getDate(), new Date().getDate());

        assertEquals(isUserMessage, jsonObject.get("isUserMessage"));
        assertEquals(text, jsonObject.get("text"));

        JSONObject user = jsonObject.getJSONObject("user");
        assertEquals(userId, user.get("id"));
        assertNotNull(user.get("name"));
        assertNotNull(user.get("isMan"));
        assertNotNull(user.get("latitude"));
        assertNotNull(user.get("longitude"));
        assertNotNull(user.get("birthYear"));
        assertNotNull(user.get("avatarName"));
        assertNotNull(user.get("playerId"));

        JSONObject company = jsonObject.getJSONObject("company");
        assertEquals(companyId, company.get("id"));
        assertNotNull(company.get("latitude"));
        assertNotNull(company.get("longitude"));
        assertNotNull(company.get("nameOfficial"));
        assertNotNull(company.get("name"));
        assertNotNull(company.get("representative"));
        assertNotNull(company.get("phone"));
        assertNotNull(company.get("email"));
        assertNotNull(company.get("inn"));
        assertNotNull(company.get("password"));
        assertNotNull(company.get("address"));
        assertNotNull(company.get("timeOfWork"));
        assertNotNull(company.get("emailConfirmed"));
        assertNotNull(company.get("playerId"));
        assertNotNull(company.get("productCategory"));
        assertNotNull(company.get("avatarName"));
    }
}
