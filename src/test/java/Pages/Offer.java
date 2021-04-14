package Pages;

import DTO.OfferDTO;
import Helpers.DateHelper;
import Tests.TestBase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Helpers.DateHelper.DAY;
import static Helpers.DateHelper.NOW;
import static Pages.Auth.getAccessToken;
import static io.restassured.RestAssured.given;

public class Offer extends TestBase {

    public static File offerImage = new File("src/test/java/resources/offer1.jpg");

    /*
    Возвращает параметры предстоящего оффера
    */
    public static Map<String, Object> getDefaultOfferParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("text", "offer_text" + getUniqueNumber(5));
        map.put("sendingTime", DateHelper.getDate(NOW - (DAY * 10)));
        map.put("timeStart", DateHelper.getDate(NOW - (DAY * 10)));
        map.put("timeEnd", DateHelper.getDate(NOW + (DAY * 10)));
        map.put("dateEnd", DateHelper.getDate(NOW + (DAY * 10)));
        map.put("dateStart", DateHelper.getDate(NOW - (DAY * 10)));
        map.put("companyId", Company.getRandomCompany().getId());
        map.put("percentage", 5);
        map.put("forMan", true);
        map.put("forWoman", true);
        map.put("upperAgeLimit", 60);
        map.put("lowerAgeLimit", 0);
        return map;
    }

    public static OfferDTO createInactiveOffer(int companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("text", "offer_text" + getUniqueNumber(5));
        params.put("sendingTime", DateHelper.getDate(NOW - (DAY * 10)));
        params.put("timeStart", DateHelper.getDate(NOW - (DAY * 9)));
        params.put("timeEnd", DateHelper.getDate(NOW - (DAY * 8)));
        params.put("dateEnd", DateHelper.getDate(NOW - (DAY * 8)));
        params.put("dateStart", DateHelper.getDate(NOW - (DAY * 9)));
        params.put("companyId", companyId);
        params.put("percentage", 7);
        params.put("forMan", true);
        params.put("forWoman", true);
        params.put("upperAgeLimit", 60);
        params.put("lowerAgeLimit", 0);

        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .formParams(params)
                .multiPart("image", offerImage)
                .when()
                .post("offer")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Создан неактивный оффер");
        return response.as(OfferDTO.class);
    }

    public static OfferDTO createActiveOffer(int companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("text", "offer_text" + getUniqueNumber(5));
        params.put("sendingTime", DateHelper.getDate(NOW + DAY * 5));
        params.put("timeStart", DateHelper.getDate(NOW + DAY * 5));
        params.put("timeEnd", DateHelper.getDate(NOW + DAY * 5));
        params.put("dateEnd", DateHelper.getDate(NOW + DAY * 5));
        params.put("dateStart", DateHelper.getDate(NOW + DAY * 5));
        params.put("companyId", companyId);
        params.put("percentage", 7);
        params.put("forMan", true);
        params.put("forWoman", true);
        params.put("upperAgeLimit", 60);
        params.put("lowerAgeLimit", 0);

        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .formParams(params)
                .multiPart("image", offerImage)
                .when()
                .post("offer")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Создан активный оффер");
        return response.as(OfferDTO.class);
    }

    public static OfferDTO createOffer(Map<String, Object> params) {
        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .formParams(params)
                .multiPart("image", offerImage)
                .when()
                .post("offer")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Создан оффер с параметрами: " + params);
        return response.as(OfferDTO.class);
    }

    public static OfferDTO createOffer() {
        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .formParams(getDefaultOfferParams())
                .multiPart("image", offerImage)
                .when()
                .post("offer")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Создан оффер с дефолтными параметрами");
        return response.as(OfferDTO.class);
    }

    public static void putOfferImage(int id, File image) {
        given()
                .spec(MULTI_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .multiPart("image", image)
                .when()
                .put("offer/" + id + "/image")
                .then()
                .statusCode(200);
        System.out.println("Обновлено изображение Оффера с id " + id);
    }

    public static OfferDTO getOffer(int id) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .when()
                .get("offer/" + id)
                .then()
                .statusCode(200)
                .extract().response();
        return response.as(OfferDTO.class);
    }

    public static ArrayList<OfferDTO> getAllOffer() {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .when()
                .get("offer")
                .then()
                .statusCode(200)
                .extract().response();
        return getListOffer(response);
    }

    public static ArrayList<OfferDTO> getListOffer(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = mapper.convertValue(
                response.as(JsonNode.class),
                new TypeReference<List<OfferDTO>>() {
                });
        return (ArrayList) list;
    }

    public static OfferDTO getRandomOffer() {
        ArrayList<OfferDTO> list = Offer.getAllOffer();
        int countUsers = list.size();
        int index = (int) (Math.random() * (countUsers - 1));
        if (index <= 0) index = 0;
        return list.get(index);
    }

    public static String getOfferImage(int id) {
        Response response = given()
                .spec(BASE_SPEC)
                .when()
                .get("Offer/" + id + "/image")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return response.asString();
    }

    public static ArrayList<OfferDTO> getOfferTOP() {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .when()
                .get("Offer/top")
                .then()
                .statusCode(200)
                .extract()
                .response();
        return getListOffer(response);
    }

    public static ArrayList<OfferDTO> getOfferTOPFromRemoteServer() {
        Response response = given()
                .spec(SERVER_SPEC)
                .header("Authorization", "Bearer " + Auth.getRemoteToken())
                .when()
                .get("Offer/top")
                .then()
                .statusCode(200)
                .extract()
                .response();
        System.out.println("Получение данных с удаленного сервера " + REMOTE_SERVER_URL);
        return getListOffer(response);
    }

    public static ArrayList<OfferDTO> getOfferByCategory(int id) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .when()
                .get("Offer/category/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();
        return getListOffer(response);
    }

    public static OfferDTO addLike(int userId, int offerId) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .body("{\n" +
                        "  \"offerId\": " + offerId + ",\n" +
                        "  \"userId\": " + userId + "\n" +
                        "}")
                .when()
                .post("offer/like")
                .then()
                .statusCode(200)
                .extract().response();
        return response.as(OfferDTO.class);
    }

    public static void addDislike(int userId, int offerId) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .body("{\n" +
                        "  \"offerId\": " + offerId + ",\n" +
                        "  \"userId\": " + userId + "\n" +
                        "}")
                .when()
                .post("offer/dislike")
                .then()
                .statusCode(200)
                .extract().response();
    }
}
