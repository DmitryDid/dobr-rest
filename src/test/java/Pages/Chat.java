package Pages;

import Constants.CONST;
import DTO.ChatsDTO;
import DTO.MessageDTO;
import DTO.MessagesDTO;
import Tests.TestBase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Chat extends TestBase {

    public static MessageDTO postUserMessage(int userId, int companyId, String text) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .body("{\n" +
                        "  \"text\": \"" + text + "\",\n" +
                        "  \"companyId\": " + companyId + ",\n" +
                        "  \"userId\": " + userId + ",\n" +
                        "  \"isUserMessage\": " + true + "\n" +
                        "}")
                .when()
                .post("Chat/message")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.as(MessageDTO.class);
    }

    public static MessageDTO postCompanyMessage(int userId, int companyId, String text) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .body("{\n" +
                        "  \"text\": \"" + text + "\",\n" +
                        "  \"companyId\": " + companyId + ",\n" +
                        "  \"userId\": " + userId + ",\n" +
                        "  \"isUserMessage\": " + false + "\n" +
                        "}")
                .when()
                .post("Chat/message")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.as(MessageDTO.class);
    }

    public static ArrayList<MessagesDTO> getMessage(int userId, int companyId, int lastMessageId) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .queryParam("userId", userId)
                .queryParam("companyId", companyId)
                .queryParam("lastMessageId", lastMessageId)
                .when()
                .get("Chat/message")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return getListMessages(response);
    }

    private static ArrayList<MessagesDTO> getListMessages(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = mapper.convertValue(
                response.as(JsonNode.class),
                new TypeReference<List<MessagesDTO>>() {
                });
        return (ArrayList) list;
    }

    public static ChatsDTO getChatCompany(int companyId, int lastId) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .queryParam("lastId", lastId)
                .when()
                .get("Chat/company/" + companyId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.as(ChatsDTO.class);
    }

    public static ChatsDTO getChatUser(int userId, int lastId) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .queryParam("lastId", lastId)
                .when()
                .get("Chat/user/" + userId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.as(ChatsDTO.class);
    }
}
