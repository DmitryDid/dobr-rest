package Pages;

import DTO.OfferDTO;
import Tests.TestBase;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static Pages.Auth.getAccessToken;
import static io.restassured.RestAssured.given;

public class Offer extends TestBase {

    public static File offerImage = new File("src/test/java/Resources/offer1.jpg");

    public static Map<String, Object> getDefaultOfferParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("text", "offer_text" + getUniqueNumber(5));
        map.put("sendingTime", "2021-03-17 11:10:13");
        map.put("timeStart", "2021-03-17 11:10:13");
        map.put("timeEnd", "2021-03-17 22:10:00");
        map.put("dateEnd", "2021-03-17 22:10:00");
        map.put("dateStart", "2021-03-17 22:10:00");
        map.put("companyId", Company.getRandomCompany().getId());
        map.put("percentage", 5);
        map.put("forMan", true);
        map.put("forWoman", true);
        map.put("upperAgeLimit", 60);
        map.put("lowerAgeLimit", 0);
        return map;
    }

    public static OfferDTO postOffer(Map<String, Object> params) {
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

    public static OfferDTO postOffer() {
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
}
