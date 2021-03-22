package Tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.MultiPartConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class TestBase {


    protected String baseURL = "https://bdobr.ru/api/v1/";
    protected String playerId = "aee5c185-29b8-4d4c-a4bf-d7641aefc556";
    protected String accessToken;

    protected RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setContentType(ContentType.JSON)
            .build();

    protected RequestSpecification multiDataSpec = new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setContentType("multipart/form-data")
            .addHeader("Accept-Charset", "UTF-8")
            .setConfig(RestAssured.config = RestAssured.config()
                    .encoderConfig(encoderConfig().defaultCharsetForContentType("UTF-8", "multipart/form-data"))
                    .encoderConfig(encoderConfig().defaultContentCharset("UTF-8"))
                    .multiPartConfig(MultiPartConfig.multiPartConfig().defaultCharset("UTF-8")))
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

    public String getToken(String playerId) {
        Response response = given()
                .spec(baseSpec)
                .when()
                .post("auth/token/user/" + playerId)
                .then()
                .statusCode(200)
                .extract().response();
        return (String) new JSONObject(response.body().asString()).get("access_token");
    }

    @Before
    public void init() {
        if (accessToken == null) {
            accessToken = getToken();
        }
    }

    public static void main(String[] args) {
        TestBase testBase = new TestBase();
        System.out.println(testBase.getUniqueNumber(13));
    }

    /*
     * length не может быть больше 13
     * */
    protected String getUniqueNumber(int length) {
        String number = String.valueOf(System.currentTimeMillis());
        int end = number.length();
        int start = end - length;
        return number.substring(start, end);
    }

    // "2021-03-22T12:59:18.9914025Z" для примера разбора строки в дату
    protected Date getDate(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date date;
        try {
            String s = stringDate.substring(0, 19);
            date = format.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
