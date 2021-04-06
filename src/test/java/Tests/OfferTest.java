package Tests;

import DTO.OfferDTO;
import Pages.Auth;
import Pages.Offer;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class OfferTest extends TestBase {

    //GET /api/v{version}/Offer/{id}
    @Test
    public void getOfferById() {
        fail();
    }

    //GET /api/v{version}/Offer/top
    @Test
    public void getOfferTop() {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getToken())
                .log().all()
                .when()
                .get("Offer/top")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(response);
        fail();
    }

    //GET /api/v{version}/Offer
    @Test
    public void getOffer() {
        fail();
    }


    //POST /api/v{version}/Offer
    @Test
    public void postOffer() {
        Map<String, Object> params = Offer.getDefaultOfferParams();
        OfferDTO offerDTO = Offer.postOffer(params);

        assertEquals(params.get("text"), offerDTO.getText());
        assertEquals(params.get("sendingTime"), offerDTO.getSendingTime().replaceAll("T", " "));
        assertEquals(params.get("timeStart"), offerDTO.getTimeStart().replaceAll("T", " "));
        assertEquals(params.get("timeEnd"), offerDTO.getTimeEnd().replaceAll("T", " "));
        assertEquals(params.get("dateEnd"), offerDTO.getDateEnd().replaceAll("T", " "));
        assertEquals(params.get("dateStart"), offerDTO.getDateStart().replaceAll("T", " "));
        assertEquals(params.get("companyId"), offerDTO.getCompany().getId());
        assertEquals(params.get("percentage"), offerDTO.getPercentage());
        assertEquals(params.get("forMan"), offerDTO.getForMan());
        assertEquals(params.get("forWoman"), offerDTO.getForWoman());
        assertEquals(params.get("UpperAgeLimit"), offerDTO.getUpperAgeLimit());
        assertEquals(params.get("LowerAgeLimit"), offerDTO.getLowerAgeLimit());
        assertTrue(offerDTO.getImage().getId() > 0);
    }

    //GET /api/v{version}/Offer/category/{id}
    @Test
    public void getOfferCategoryById() {
        fail();
    }

    //GET /api/v{version}/Offer/{id}/image
    @Test
    public void getOfferImage() {
        String id = "1";

        Response response = given()
                .spec(BASE_SPEC)
                .log().all()
                .when()
                .get("Offer/" + id + "/image")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
        fail();
    }

    //PUT /api/v{version}/Offer/{id}/image
    @Test
    public void putOfferImageById() {
        fail();
    }

    //POST /api/v{version}/Offer/like
    @Test
    public void postOfferLike() {
        fail();
    }

    //POST /api/v{version}/Offer/dislike
    @Test
    public void postOfferDisLike() {
        fail();
    }
}
