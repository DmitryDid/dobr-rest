package Pages;

import Constants.CONST;
import DTO.AuthDTO;
import DTO.CompanyDTO;
import Helpers.DBHelpers;
import Tests.TestBase;
import io.restassured.response.Response;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class Company extends TestBase {

    static String id = getUniqueNumber(5);
    static String name = "test_company_name";
    static String nameOfficial = "test_company nameOfficial";
    static String latitude = "55.0415000";
    static String longitude = "82.9346000";
    static String representative = "test_company_representative";
    static String phone = getUniqueNumber(9);
    static String password = "test_company_password";
    static String address = "test_company_address";
    static String timeOfWork = "8-22";
    static int productCategoryId = 1;
    static String playerId = UUID.randomUUID().toString();
    static String fileName = "appleGoogle.jpg";
    static File image = new File("src/test/java/Resources/" + fileName);


    public static Response getAllCompany() {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }

    public static Response getCompanyById(String id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/" + id)
                .then()
                .extract()
                .response();
        return response;
    }

    public static Response updateCompanyById(String id, Map data) {
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .formParams(data)
                .multiPart("image", image)
                .when()
                .put("Company/" + id)
                .then()
                .extract().response();

        if (response.getStatusCode() != 200) System.err.println(response.body().asString());
        return response;
    }

    public static Response createCompany() {
        String email = getUniqueNumber(10);
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .formParam("name", name)
                .formParam("nameOfficial", nameOfficial)
                .formParam("Latitude", latitude)
                .formParam("Longitude", longitude)
                .formParam("representative", representative)
                .formParam("phone", phone)
                .formParam("email", CONST.EMAIL)
                .formParam("inn", email)
                .formParam("password", password)
                .formParam("address", address)
                .formParam("timeOfWork", timeOfWork)
                .formParam("productCategoryId", productCategoryId)
                .formParam("playerId", playerId)
                .multiPart("image", image)
                .when()
                .post("Company")
                .then()
                .extract().response();

        if (response.getStatusCode() != 200) {
            return createCompanyWithEmail(getUniqueNumber(10) + "@mail.ru");
        }
        System.out.println("Создана компания " + email);
        return response;
    }

    public static Response createCompanyWithEmail(String email) {
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .formParam("name", name)
                .formParam("nameOfficial", nameOfficial)
                .formParam("Latitude", latitude)
                .formParam("Longitude", longitude)
                .formParam("representative", representative)
                .formParam("phone", phone)
                .formParam("email", email)
                .formParam("inn", getUniqueNumber(10))
                .formParam("password", password)
                .formParam("address", address)
                .formParam("timeOfWork", timeOfWork)
                .formParam("productCategoryId", productCategoryId)
                .formParam("playerId", playerId)
                .multiPart("image", image)
                .when()
                .post("Company")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Создана компания " + email);
        return response;
    }

    public static Response getCompanyTop() {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/top")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response;
    }

    public static Response deleteCompanyById(String id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .delete("Company/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        System.out.println("Удалена компания " + id);
        return response;
    }

    public static Response getCompanyImageById(String id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/" + id + "/image")
                .then()
                //.statusCode(200)
                .extract()
                .response();
        if (response.getStatusCode() != 200) System.err.println(response.asString());
        return response;
    }

    public static Response getCompanyCategoryById(String id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/category/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);
        return response;
    }
}