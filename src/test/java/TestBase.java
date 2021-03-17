import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class TestBase {

    String baseURL = "https://bdobr.ru/api/v1/";
    String playerId = "aee5c185-29b8-4d4c-a4bf-d7641aefc556";

    RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setContentType(ContentType.JSON)
            .build();

    RequestSpecification multiDataSpec = new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .build();

    protected void toConsole(Object object) {
        String response = "не удалось получить тело ответа";
        if (object instanceof Response) {
            response = ((Response) object).body().asString();
        }
        if (object instanceof String) {
            response = (String) object;
        }
        if (object instanceof JSONObject) {
            response = object.toString();
            System.out.println("json:\n" + response);
            return;
        }
        System.out.println("toConsole:\n" + response);
        System.out.println();
    }

    public String getToken() {
        Response response = given()
                .spec(baseSpec)
                .when()
                .post("auth/token/user/" + playerId)
                .then()
                .statusCode(200)
                .extract().response();
        return (String) new JSONObject(response.body().asString()).get("access_token");
    }
}
