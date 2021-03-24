package Tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.MultiPartConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class TestBase {


    /*protected String baseURL = "https://bdobr.ru/api/v1/";*/

    protected String baseURL = "http://192.168.1.232/api/v1/";
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

    @Before
    public void init() {
        if (accessToken == null) {
            accessToken = getToken();
        }
        createProductCategory();
        createCompany();
    }

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
        System.out.println("\ntoConsole:\n" + response);
        System.out.println();
    }

    public String getToken() {
        if (accessToken != null) return accessToken;
        Response response = given()
                .spec(baseSpec)
                .when()
                .post("auth/token/user/" + playerId)
                .then()
                .extract().response();

        if (response.getStatusCode() != 200) {
            createUser();
            response = given()
                    .spec(baseSpec)
                    .when()
                    .post("auth/token/user/" + playerId)
                    .then()
                    .statusCode(200)
                    .extract().response();
            return (String) new JSONObject(response.body().asString()).get("access_token");
        }
        return (String) new JSONObject(response.body().asString()).get("access_token");
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

    private void createUser() {
        String id = "1";
        String name = "Did";
        boolean isMan = true;
        String birthYear = "1986-07-10T12:59:18.99";
        String fileName = "hacker.jpg";
        File image = new File("src/test/java/Resources/" + fileName);
        String latitude = "55.0415000";
        String longitude = "82.9346000";

        given()
                .spec(multiDataSpec)
                .formParam("id", id)
                .formParam("name", name)
                .formParam("isMan", isMan)
                .formParam("playerId", playerId)
                .formParam("BirthYear", birthYear)
                .formParam("latitude", latitude)
                .formParam("longitude", longitude)
                .multiPart("image", image)
                .when()
                .post("User")
                .then()
                .statusCode(200);
        System.out.println("\nСоздан пользователь " + name);
        System.out.println();
    }

    private void createProductCategory() {
        Response getResponse = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get("productCategory")
                .then()
                .statusCode(200)
                .extract().response();

        if (new JSONArray(getResponse.asString()).length() == 0) {
            String name = "alcohol";
            int ageLimit = 18;
            String fileName = "alko.jpg";
            File image = new File("src/test/java/Resources/" + fileName);

            given()
                    .spec(multiDataSpec)
                    .header("Authorization", "Bearer " + getToken())
                    .formParam("name", name)
                    .formParam("ageLimit", ageLimit)
                    .multiPart("image", image)
                    .when()
                    .post("productCategory")
                    .then()
                    .statusCode(200);
            System.out.println("\nСоздана категория продукта " + name);
            System.out.println();
        }
    }

    private void createCompany() {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get("Company")
                .then()
                .statusCode(200)
                .extract().response();

        if (new JSONArray(response.asString()).length() == 0) {
            String id = getUniqueNumber(3);
            String name = "test_company_name";
            String nameOfficial = "test_company nameOfficial";
            String latitude = "55.0415000";
            String longitude = "82.9346000";
            String representative = "test_company_representative";
            String phone = getUniqueNumber(8);
            String email = getUniqueNumber(6) + "@mail.ru";
            String inn = getUniqueNumber(10);
            String password = "test_company_password";
            String address = "test_company_address";
            String timeOfWork = "8-22";
            String productCategoryId = "1";
            String playerId = UUID.randomUUID().toString();
            String fileName = "appleGoogle.jpg";
            File image = new File("src/test/java/Resources/" + fileName);

            given()
                    .spec(multiDataSpec)
                    .formParam("id", id)
                    .formParam("name", name)
                    .formParam("nameOfficial", nameOfficial)
                    .formParam("Latitude", latitude)
                    .formParam("Longitude", longitude)
                    .formParam("representative", representative)
                    .formParam("phone", phone)
                    .formParam("email", email)
                    .formParam("inn", inn)
                    .formParam("password", password)
                    .formParam("address", address)
                    .formParam("timeOfWork", timeOfWork)
                    .formParam("productCategoryId", productCategoryId)
                    .formParam("playerId", playerId)
                    .multiPart("image", image)
                    .when()
                    .post("Company")
                    .then()
                    .statusCode(200);
            System.out.println("\nСоздана компания " + name);
            System.out.println();
        }
    }
}
