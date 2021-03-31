package Pages;

import Constants.CONST;
import Tests.TestBase;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class Company extends TestBase {

    static public Map getDefaultParams() {
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
        System.out.println("Создана компания");
        return response;
    }

    public static Response createCompanyWithEmail(String email) {
        Map params = getDefaultParams();
        params.put("email", email);

        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .formParams(params)
                .multiPart("image", image)
                .when()
                .post("Company")
                .then()
                //.statusCode(200)
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