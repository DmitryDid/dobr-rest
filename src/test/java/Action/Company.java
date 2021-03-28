package Action;

import Constants.CONST;
import DTO.AuthDTO;
import Helpers.DBHelpers;
import Tests.TestBase;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.File;
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
    static String productCategoryId = "1";
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

    public static Response getCompanyById(int id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response;
    }

    public static Response createCompany() {
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .formParam("id", id)
                .formParam("name", name)
                .formParam("nameOfficial", nameOfficial)
                .formParam("Latitude", latitude)
                .formParam("Longitude", longitude)
                .formParam("representative", representative)
                .formParam("phone", phone)
                .formParam("email", CONST.EMAIL)
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
                .extract().response();

        if (response.getStatusCode() != 200) {
            return createCompanyWithEmail(getUniqueNumber(10) + "mail.ru");
        }

        int companyId = (Integer) new JSONObject(response).getJSONObject("company").get("id");
        DBHelpers.confirmEmailCompanyById(companyId);
        System.out.println("Создана компания " + name);
        return response;
    }

    public static Response createCompanyWithEmail(String email) {
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .formParam("id", id)
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
                .extract().response();

        AuthDTO authDTO = response.as(AuthDTO.class);

        int companyId = authDTO.getCompany().getId();

        DBHelpers.confirmEmailCompanyById(companyId);
        System.out.println("Создана компания " + companyId);
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

    public static Response deleteCompanyById(int id) {
        Response rsponse = given()
                .spec(CONST.BASE_SPEC)
                .when()
                .delete("Company/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return rsponse;
    }

    public static Response getCompanyImageById(int id) {
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
}