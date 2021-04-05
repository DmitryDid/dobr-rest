package Tests;

import Pages.Auth;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.fail;

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
        fail();
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
