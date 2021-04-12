package Tests;

import DTO.OfferDTO;
import Pages.Offer;
import Pages.User;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static Pages.Offer.*;
import static org.junit.Assert.*;

public class OfferTest extends TestBase {

    //POST /api/v{version}/Offer
    @Test
    public void postOffer() {
        Map<String, Object> params = getDefaultOfferParams();
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
        assertEquals(params.get("upperAgeLimit"), offerDTO.getUpperAgeLimit());
        assertEquals(params.get("lowerAgeLimit"), offerDTO.getLowerAgeLimit());
        assertTrue(offerDTO.getImage().getId() > 0);
    }

    //GET /api/v{version}/Offer/{id}
    @Test
    public void getOfferById() {
        Integer id = getRandomOffer().getId();
        getOffer(id);
    }

    //GET /api/v{version}/Offer/top
    @Test
    public void getOfferTop() {
        getOfferTOP();
    }

    //GET /api/v{version}/Offer
    @Test
    public void getAllOffer() {
        Offer.getAllOffer();
    }

    //GET /api/v{version}/Offer/category/{id}
    @Test
    public void getOfferCategoryById() {
        Integer id = getRandomOffer().getId();
        getOfferByCategory(id);
    }

    //GET /api/v{version}/Offer/{id}/image
    @Test
    public void getOfferImage() {
        Integer id = getRandomOffer().getId();
        Offer.getOfferImage(id);
    }

    //PUT /api/v{version}/Offer/{id}/image
    @Test
    public void putOfferImageById() {
        Integer id = getRandomOffer().getId();
        File image = new File("src/test/java/Resources/newOffer.jpeg");
        putOfferImage(id, image);
    }

    //POST /api/v{version}/Offer/like
    @Test
    public void postOfferLike() {
        Integer userId = User.getRandomUser().getId();
        Integer OfferId = Offer.getRandomOffer().getId();
        Offer.addLike(userId, OfferId);
        Offer.addLike(userId, OfferId);
        Offer.addLike(userId, OfferId);

    }

    //POST /api/v{version}/Offer/dislike
    @Test
    public void postOfferDisLike() {
        Integer userId = User.getRandomUser().getId();
        Integer OfferId = Offer.getRandomOffer().getId();
        Offer.addDislike(userId, OfferId);
        Offer.addDislike(userId, OfferId);
    }
}
