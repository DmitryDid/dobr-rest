package Pages;

import Constants.CONST;
import DTO.AuthDTO;
import DTO.CompanyDTO;
import Tests.TestBase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.given;

public class Company extends TestBase {

    static public Map<String, Object> getDefaultParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "test_company_name");
        map.put("nameOfficial", "test_company nameOfficial");
        map.put("inn", getUniqueNumber(9));
        map.put("latitude", 55.0415000);
        map.put("longitude", 82.9346000);
        map.put("representative", "test_company_representative");
        map.put("phone", getUniqueNumber(9));
        map.put("password", "test_company_password");
        map.put("address", "test_company_address");
        map.put("email", getUniqueNumber(7) + "@mail.ru");
        map.put("timeOfWork", "8-22");
        map.put("productCategoryId", 1);
        map.put("playerId", UUID.randomUUID().toString());
        return map;
    }

    static File image = new File("src/test/java/Resources/appleGoogle.jpg");


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
                .statusCode(200)
                .extract()
                .response();
        return response;
    }

    public static Response updateCompanyById(String id, Map data, File image) {
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .formParams(data)
                .multiPart("image", image)
                .when()
                .put("Company/" + id)
                .then()
                .statusCode(200)
                .extract().response();

        return response;
    }

    public static Response createCompany(Map<String, Object> params) {
        Map defaultParams = getDefaultParams();

        for (Map.Entry<String, Object> pair : params.entrySet()) {
            String key = pair.getKey();
            Object value = pair.getValue();
            defaultParams.put(key, value);
        }

        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .formParams(defaultParams)
                .multiPart("image", image)
                .when()
                .post("Company")
                .then()
                .extract().response();

        if (response.getStatusCode() != 200) {
            return createCompanyWithEmail(getUniqueNumber(10) + "@mail.ru");
        }
        System.out.println("Создана компания " + response.as(AuthDTO.class).getCompany().getEmail());
        return response;
    }

    public static Response createCompanyWithEmail(String email) {
        Map<String, Object> params = getDefaultParams();
        params.put("email", email);

        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .formParams(params)
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

    public static Response deleteCompanyById(String id, int statusCode) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .delete("Company/" + id)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
        return response;
    }

    public static Response getCompanyImageById(String id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/" + id + "/image")
                .then()
                .statusCode(200)
                .extract()
                .response();
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
        return response;
    }

    public static Response getCompanyNumberOfFavorites(int id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/" + id + "/number-of-favorites")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response;
    }

    public static ArrayList getListCompany(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = mapper.convertValue(
                response.as(JsonNode.class),
                new TypeReference<List<CompanyDTO>>() {
                });
        return (ArrayList) list;
    }
}