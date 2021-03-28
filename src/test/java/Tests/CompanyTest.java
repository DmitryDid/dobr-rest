package Tests;

import Action.Company;
import Constants.CONST;
import DTO.AuthDTO;
import DTO.CompanyDTO;
import DTO.FailDTO;
import Helpers.DBHelpers;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompanyTest extends TestBase {

    @Test
    public void deleteCompanyCascade() {
        String id = getUniqueNumber(3);
        String name = "test_name";
        String nameOfficial = "test_nameOfficial";
        String latitude = "55.0415000";
        String longitude = "82.9346000";
        String representative = "test_representative";
        String phone = "19770100";
        String email = getUniqueNumber(6) + "@mail.ru";
        String inn = getUniqueNumber(10);
        String password = "test_password";
        String address = "test_address";
        String timeOfWork = "8-22";
        String productCategoryId = "1";
        String playerId = "2";
        String fileName = "Kiprensky_Pushkin.jpg";
        File image = new File("src/test/java/Resources/" + fileName);

        // POST /api/v{version}/Company
        Response postResponse = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .log().all()
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
                .statusCode(200)
                .extract()
                .response();
        toConsole(postResponse);

        Integer returnedId = (Integer) new JSONObject(postResponse.asString()).getJSONObject("company").get("id");

        // DELETE /api/v{version}/Company/cascade/{id}
        Response deleteResponse = given()
                .spec(CONST.BASE_SPEC)
                .log().all()
                .when()
                .delete("Company/cascade/" + returnedId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(deleteResponse);

        Response get2Response = given()
                .spec(CONST.BASE_SPEC)
                .log().uri().log().method()
                .when()
                .get("Company/" + returnedId)
                .then()
                .statusCode(404)
                .extract()
                .response();
        toConsole(get2Response);
    }

    // /api/v{version}/Company/{id}/number-of-favorites
    @Test
    public void getCompanyNumberOfFavoritesById() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 10; i++) {
            Response response = given()
                    .spec(CONST.BASE_SPEC)
                    .log().uri()
                    .when()
                    .get("Company/" + i + "/number-of-favorites")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();
            toConsole(response);
            int current = Integer.parseInt(response.asString());
            max = Math.max(max, current);
        }
        assertTrue(max > 0);
    }

    // /api/v{version}/Company/{id}/notification
    // пока нет ни одного уведомления - проверяем так.
    @Test
    public void getCompanyNotificationById() {
        for (int i = 0; i < 50; i++) {
            Response response = given()
                    .spec(CONST.BASE_SPEC)
                    .log().uri()
                    .when()
                    .get("Company/" + i + "/notification")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();
            toConsole(response);
        }
    }

    // /api/v{version}/Company
    @Test
    public void getCompanyAll() {
        ArrayList companyList = getListDTO(Company.getAllCompany());

        CompanyDTO currentCompany;

        for (int i = 0; i < companyList.size(); i++) {
            currentCompany = (CompanyDTO) companyList.get(i);
            if (currentCompany.getEmail().equals(CONST.EMAIL)) {
                assertEquals((Integer) 1, currentCompany.getId());
                assertEquals((Double) 55.0415, currentCompany.getLatitude());
                assertEquals((Double) 82.9346, currentCompany.getLongitude());
                assertEquals("test_company nameOfficial", currentCompany.getNameOfficial());
                assertEquals("test_company_representative", currentCompany.getRepresentative());
                assertTrue(Long.parseLong(currentCompany.getPhone()) >= 0);
                assertTrue(Long.parseLong(currentCompany.getInn()) >= 0);
                assertEquals("test_company_password", currentCompany.getPassword());
                assertEquals("test_company_address", currentCompany.getAddress());
                assertEquals("8-22", currentCompany.getTimeOfWork());
                assertTrue(currentCompany.getEmailConfirmed());
                assertTrue(currentCompany.getPlayerId().length() > 20);
                assertEquals((Integer) 1, currentCompany.getProductCategory().getId());
                assertEquals((Integer) 3, currentCompany.getImageId());
                //    assertEquals(currentCompany.getImage().getId(), currentCompany.getImageId()); //TODO: похоже кривая запись. потом проверить
                return;
            }
        }
        Assert.fail();
    }

    // /api/v1/company/top
    @Test
    public void getCompanyTop() {
        ArrayList companyList = getListDTO(Company.getCompanyTop());
        assertTrue(companyList.size() > 0); //TODO: пока нет записей
    }

    // /api/v{version}/Company/{id}/image
    @Test
    public void getCompanyImageById() {

        AuthDTO dto = Company.getCompanyImageById(5).as(AuthDTO.class);
        System.out.println(dto); //TODO: не работает
    }

    // /api/v{version}/Company/category/{id}
    @Test
    public void getCompanyCategoryById() {
        String id = getUniqueNumber(3);
        String name = "test_name";
        String nameOfficial = "test_nameOfficial";
        String latitude = "55.0415000";
        String longitude = "82.9346000";
        String representative = "test_representative";
        String phone = "19770100";
        String email = getUniqueNumber(6) + "@mail.ru";
        String inn = getUniqueNumber(10);
        String password = "test_password";
        String address = "test_address";
        String timeOfWork = "8-22";
        String productCategoryId = "5";
        String playerId = "2";
        String fileName = "Kiprensky_Pushkin.jpg";
        File image = new File("src/test/java/Resources/" + fileName);

        // POST /api/v{version}/Company
        Response postResponse = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .log().all()
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
                .statusCode(200)
                .extract()
                .response();
        toConsole(postResponse);

        Integer returnedId = (Integer) new JSONObject(postResponse.asString()).getJSONObject("company").get("id");

        Response response = given()
                .spec(CONST.BASE_SPEC)
                .log().all()
                .when()
                .get("Company/category/" + productCategoryId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);

        JSONArray jsonArray = new JSONArray(response.asString());
        assertTrue(jsonArray.length() > 0);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            assertEquals("5", jsonObject.getJSONObject("productCategory").get("id").toString());

            if (jsonObject.get("id").equals(returnedId)) {
                assertEquals(returnedId, jsonObject.get("id"));
                assertTrue(latitude.contains(jsonObject.get("latitude").toString()));
                assertTrue(longitude.contains(jsonObject.get("longitude").toString()));
                assertEquals(nameOfficial, jsonObject.get("nameOfficial"));
                assertEquals(name, jsonObject.get("name"));
                assertEquals(representative, jsonObject.get("representative"));
                assertEquals(phone, jsonObject.get("phone"));
                assertEquals(email, jsonObject.get("email"));
                assertEquals(inn, jsonObject.get("inn"));
                assertEquals(password, jsonObject.get("password"));
                assertEquals(address, jsonObject.get("address"));
                assertEquals(timeOfWork, jsonObject.get("timeOfWork"));
                assertEquals(false, jsonObject.get("emailConfirmed"));
                assertEquals(playerId, jsonObject.get("playerId"));
                assertTrue(jsonObject.get("avatarName").toString().contains(fileName));
            }
        }

        // DELETE /api/v{version}/Company/{id}
        Response deleteResponse = given()
                .spec(CONST.BASE_SPEC)
                .log().uri().log().method()
                .when()
                .delete("Company/" + returnedId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(deleteResponse);
    }

    // /api/v{version}/Company/{id}/offer
    @Test
    public void getCompanyOfferById() {
        String id = "5";
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .log().uri().log().method()
                .when()
                .get("Company/" + id + "/offer")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);

        JSONObject jsonObject = new JSONObject(response.asString());

        JSONArray preOffer = jsonObject.getJSONArray("preOffer");
        for (int i = 0; i < preOffer.length(); i++) {
            assertEquals(id, preOffer.getJSONObject(i).getJSONObject("company").get("id").toString());
            System.out.println("preOffer - true");
        }

        JSONArray activeOffer = jsonObject.getJSONArray("activeOffer");
        for (int i = 0; i < activeOffer.length(); i++) {
            assertEquals(id, activeOffer.getJSONObject(i).getJSONObject("company").get("id").toString());
            System.out.println("activeOffer - true");
        }

        JSONArray inactiveOffer = jsonObject.getJSONArray("inactiveOffer");
        for (int i = 0; i < inactiveOffer.length(); i++) {
            assertEquals(id, inactiveOffer.getJSONObject(i).getJSONObject("company").get("id").toString());
            System.out.println("inactiveOffer - true");
        }

        try {
            JSONArray nearbyOffer = jsonObject.getJSONArray("nearbyOffer");
            for (int i = 0; i < nearbyOffer.length(); i++) {
                assertEquals(id, nearbyOffer.getJSONObject(i).getJSONObject("company").get("id").toString());
                System.out.println("nearbyOffer - true");
            }
        } catch (JSONException e) {
            System.out.print(e.getMessage());
            System.out.println(" is " + jsonObject.get("nearbyOffer"));
        }
    }

    @Test
    public void deleteCompany() {
        CompanyDTO companyDTO = Company.createCompany().as(CompanyDTO.class);
        int companyId = companyDTO.getId();
        DBHelpers.confirmEmailCompanyById(companyId);

        Company.deleteCompanyById(companyId);

        FailDTO failDTO = Company.getCompanyById(companyId).as(FailDTO.class);
        System.out.println(failDTO);

    }

    // /api/v{version}/Company/{id}
    @Test
    public void putCompanyById() {
        String id = getUniqueNumber(3);
        String name = "test_name";
        String nameOfficial = "test_nameOfficial";
        String latitude = "55.0415000";
        String longitude = "82.9346000";
        String representative = "test_representative";
        String phone = "19770100";
        String email = getUniqueNumber(6) + "@mail.ru";
        String inn = getUniqueNumber(10);
        String password = "test_password";
        String address = "test_address";
        String timeOfWork = "8-22";
        String productCategoryId = "1";
        String playerId = "2";
        String fileName = "Kiprensky_Pushkin.jpg";
        File image = new File("src/test/java/Resources/" + fileName);

        // POST /api/v{version}/Company
        Response postResponse = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .log().all()
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
                .statusCode(200)
                .extract()
                .response();
        toConsole(postResponse);

        Integer returnedId = (Integer) new JSONObject(postResponse.asString()).getJSONObject("company").get("id");

        // PUT /api/v{version}/Company/{id}
        String newPhone = "1122334455";
        String newName = "NEW_TEST_NAME";
        String newAddress = "NEW_TEST_ADDRESS";
        String newInn = getUniqueNumber(10);
        Response putResponse = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .log().all()
                .config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
                .formParam("id", id)
                .formParam("name", newName)
                .formParam("nameOfficial", nameOfficial)
                .formParam("Latitude", latitude)
                .formParam("Longitude", longitude)
                .formParam("representative", representative)
                .formParam("phone", newPhone)
                .formParam("email", email)
                .formParam("inn", newInn)
                .formParam("password", password)
                .formParam("address", newAddress)
                .formParam("timeOfWork", timeOfWork)
                .formParam("productCategoryId", productCategoryId)
                .formParam("playerId", playerId)
                .multiPart("image", image)
                .when()
                .put("Company/" + returnedId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(putResponse);

        // GET /api/v{version}/Company/{id}
        Response getResponse = given()
                .spec(CONST.BASE_SPEC)
                .log().uri().log().method()
                .when()
                .get("Company/" + returnedId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(getResponse);

        JSONObject getJSON = new JSONObject(getResponse.asString());
        JSONObject productCategory = getJSON.getJSONObject("productCategory");

        assertEquals(returnedId, getJSON.get("id"));
        assertTrue(latitude.contains(getJSON.get("latitude").toString()));
        assertTrue(longitude.contains(getJSON.get("longitude").toString()));
        assertEquals(nameOfficial, getJSON.get("nameOfficial"));
        assertEquals(newName, getJSON.get("name"));
        assertEquals(representative, getJSON.get("representative"));
        assertEquals(newPhone, getJSON.get("phone"));
        assertEquals(email, getJSON.get("email"));
        assertEquals(newInn, getJSON.get("inn"));
        assertEquals(password, getJSON.get("password"));
        assertEquals(newAddress, getJSON.get("address"));
        assertEquals(timeOfWork, getJSON.get("timeOfWork"));
        assertEquals(false, getJSON.get("emailConfirmed"));
        assertEquals(playerId, getJSON.get("playerId"));
        assertTrue(getJSON.get("avatarName").toString().contains(fileName));

        assertEquals("1", productCategory.get("id").toString());
        assertEquals("Аптеки", productCategory.get("name"));
        assertEquals("1-1.jpg", productCategory.get("imageName"));

        // PUT /api/v{version}/Company/{id}/image
        String newImageName = "Kiprensky_Pushkin_2.jpg";
        File newImage = new File("src/test/java/Resources/" + newImageName);
        Response putImageResponse = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .log().all()
                .multiPart("image", newImage)
                .when()
                .put("Company/" + returnedId + "/image")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(putImageResponse);

        // GET /api/v{version}/Company/{id}
        Response getImageResponse = given()
                .spec(CONST.BASE_SPEC)
                .log().uri().log().method()
                .when()
                .get("Company/" + returnedId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(getImageResponse);

        JSONObject getImageJSON = new JSONObject(getImageResponse.asString());
        assertTrue(getImageJSON.get("avatarName").toString().contains(newImageName));

        // DELETE /api/v{version}/Company/{id}
        Response deleteResponse = given()
                .spec(CONST.BASE_SPEC)
                .log().uri().log().method()
                .when()
                .delete("Company/" + returnedId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(deleteResponse);
    }
}
