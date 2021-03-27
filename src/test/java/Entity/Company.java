package Entity;

import Constants.Const;
import Tests.TestBase;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.File;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class Company extends TestBase {

    String id = getUniqueNumber(3);
    String name = "test_company_name";
    String nameOfficial = "test_company nameOfficial";
    String latitude = "55.0415000";
    String longitude = "82.9346000";
    String representative = "test_company_representative";
    String phone = getUniqueNumber(8);

    String password = "test_company_password";
    String address = "test_company_address";
    String timeOfWork = "8-22";
    String productCategoryId = "1";
    String playerId = UUID.randomUUID().toString();
    String fileName = "appleGoogle.jpg";
    File image = new File("src/test/java/Resources/" + fileName);

    public Response getAllCompany() {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + Auth.accessToken)
                .when()
                .get("Company")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }

    public Response getCompanyById(int id) {
        Response response = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + Auth.accessToken)
                .when()
                .get("Company/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response;
    }

    public Response createCompany() {
        Response response = given()
                .spec(multiDataSpec)
                .formParam("id", id)
                .formParam("name", name)
                .formParam("nameOfficial", nameOfficial)
                .formParam("Latitude", latitude)
                .formParam("Longitude", longitude)
                .formParam("representative", representative)
                .formParam("phone", phone)
                .formParam("email", Const.EMAIL)
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
            return createCompanyWithEmail(getUniqueNumber(7) + "mail.ru");
        }

        int companyId = (Integer) new JSONObject(response).getJSONObject("company").get("id");
        dbHelpers.confirmEmailCompanyById(companyId);
        System.out.println("Создана компания " + name);
        return response;
    }

    public Response createCompanyWithEmail(String email) {
        Response response = given()
                .spec(multiDataSpec)
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
                .statusCode(200)
                .extract().response();
        toConsole(response);
        int companyId = response.jsonPath().get("company.id");
        dbHelpers.confirmEmailCompanyById(companyId);
        System.out.println("Создана компания " + companyId);
        return response;
    }
}