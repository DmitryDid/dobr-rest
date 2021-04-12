package Pages;

import DTO.*;
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
        map.put("email", getUniqueNumber(5) + EMAIL);
        map.put("timeOfWork", "8-22");
        map.put("productCategoryId", 1);
        map.put("playerId", UUID.randomUUID().toString());
        return map;
    }

    static File image = new File("src/test/java/Resources/appleGoogle.jpg");


    public static ArrayList<CompanyDTO> getAllCompany() {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company")
                .then()
                .statusCode(200)
                .extract().response();
        return getListCompany(response);
    }

    public static CompanyDTO getCompany(int id) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.as(CompanyDTO.class);
    }

    public static Response updateCompany(int id, Map data, File image) {
        Response response = given()
                .spec(MULTI_DATA_SPEC)
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

    public static AuthDTO createCompany() {
        Map<String, Object> defaultParams = getDefaultParams();
        AuthDTO authDTO;
        try {
            authDTO = given()
                    .spec(MULTI_DATA_SPEC)
                    .formParams(defaultParams)
                    .multiPart("image", image)
                    .when()
                    .post("Company")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response().as(AuthDTO.class);
        } catch (AssertionError e) {
            authDTO = createCompanyWithEmail(getUniqueNumber(9) + "@create.ru");
        }

        System.out.println("Создана компания " + authDTO.getCompany().getEmail());
        return authDTO;
    }

    public static AuthDTO createCompany(Map<String, Object> params) {
        Map<String, Object> defaultParams = getDefaultParams();

        for (Map.Entry<String, Object> pair : params.entrySet()) {
            String key = pair.getKey();
            Object value = pair.getValue();
            defaultParams.put(key, value);
        }

        AuthDTO authDTO = given()
                .spec(MULTI_DATA_SPEC)
                .formParams(defaultParams)
                .multiPart("image", image)
                .when()
                .post("Company")
                .then()
                .statusCode(200)
                .extract()
                .response().as(AuthDTO.class);

        System.out.println("Создана компания " + authDTO.getCompany().getEmail());
        return authDTO;
    }

    public static AuthDTO createCompanyWithEmail(String email) {
        Map<String, Object> params = getDefaultParams();
        params.put("email", email);
        params.put("inn", getUniqueNumber(9));

        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .formParams(params)
                .multiPart("image", image)
                .when()
                .post("Company")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Создана компания " + email);
        return response.as(AuthDTO.class);
    }

    public static ArrayList<TopDTO> getCompanyTop() {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/top")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return getListCompanyTop(response);
    }

    public static Response deleteCompany(int id, int statusCode) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .delete("Company/" + id)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
        return response;
    }

    public static Response getCompanyImage(int id) {
        Response response = given()
                .spec(BASE_SPEC)
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
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/category/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response;
    }

    public static Integer getCompanyNumberOfFavorites(int id) {
        String response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/" + id + "/number-of-favorites")
                .then()
                .statusCode(200)
                .extract()
                .response().body().asString();
        return Integer.valueOf(response);
    }

    public static ArrayList<CompanyDTO> getListCompany(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = mapper.convertValue(
                response.as(JsonNode.class),
                new TypeReference<List<CompanyDTO>>() {
                });
        return (ArrayList) list;
    }

    public static ArrayList<CompanyListDTO> getListStories(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = mapper.convertValue(
                response.as(JsonNode.class),
                new TypeReference<List<CompanyListDTO>>() {
                });
        return (ArrayList) list;
    }

    public static ArrayList<TopDTO> getListCompanyTop(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = mapper.convertValue(
                response.as(JsonNode.class),
                new TypeReference<List<TopDTO>>() {
                });
        return (ArrayList) list;
    }

    public static CompanyDTO getRandomCompany() {
        ArrayList<CompanyDTO> list = Company.getAllCompany();
        int countCompany = list.size();
        int index = (int) (Math.random() * (countCompany - 1));
        if (index <= 0) index = 0;
        return list.get(index);
    }

    public static Response updateCompanyImage(int id, File image) {
        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .multiPart("image", image)
                .when()
                .put("Company/" + id + "/image")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }

    public static ArrayList<CompanyDTO> getListCompanyCategory(int id) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/category/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();

        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = mapper.convertValue(
                response.as(JsonNode.class),
                new TypeReference<List<CompanyDTO>>() {
                });
        return (ArrayList) list;
    }

    public static int getCountCompany() {
        return getAllCompany().size();
    }

    public static Response deleteCompanyCascade(int id, int statusCode) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .delete("Company/cascade/" + id)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
        return response;
    }

    public static void getCompanyNotification(int id) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/" + id + "/notification")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);
    }

    public static CompanyOfferDTO getCompanyOffer(int id) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("Company/" + id + "/offer")
                .then()
//                .statusCode(200)
                .extract()
                .response();
        return response.as(CompanyOfferDTO.class);
    }
}