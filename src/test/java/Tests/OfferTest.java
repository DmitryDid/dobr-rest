package Tests;

import DTO.CompanyOfferDTO;
import DTO.OfferDTO;
import Helpers.DateHelper;
import Pages.Offer;
import Pages.ProductCategory;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static Pages.Company.*;
import static Pages.Offer.*;
import static Pages.User.createUser;
import static Pages.User.getRandomUser;
import static org.junit.Assert.*;

public class OfferTest extends TestBase {

    @Test
    public void postOffer() {
        Map<String, Object> params = getDefaultOfferParams();
        OfferDTO offer = createOffer(params);

        assertEquals(params.get("text"), offer.getText());
        assertEquals(params.get("sendingTime"), offer.getSendingTime().replaceAll("T", " "));
        assertEquals(params.get("timeStart"), offer.getTimeStart().replaceAll("T", " "));
        assertEquals(params.get("timeEnd"), offer.getTimeEnd().replaceAll("T", " "));
        assertEquals(params.get("dateEnd"), offer.getDateEnd().replaceAll("T", " "));
        assertEquals(params.get("dateStart"), offer.getDateStart().replaceAll("T", " "));
        assertEquals(params.get("companyId"), offer.getCompany().getId());
        assertEquals(params.get("percentage"), offer.getPercentage());
        assertEquals(params.get("forMan"), offer.getForMan());
        assertEquals(params.get("forWoman"), offer.getForWoman());
        assertEquals(params.get("upperAgeLimit"), offer.getUpperAgeLimit());
        assertEquals(params.get("lowerAgeLimit"), offer.getLowerAgeLimit());
        assertTrue(offer.getImageId() > 0);
    }

    @Test
    public void getOfferById() {
        Map<String, Object> params = getDefaultOfferParams();
        Integer offerId = createOffer(params).getId();

        OfferDTO offer = getOffer(offerId);

        assertEquals(params.get("text"), offer.getText());
        assertEquals(params.get("sendingTime"), offer.getSendingTime().replaceAll("T", " "));
        assertEquals(params.get("timeStart"), offer.getTimeStart().replaceAll("T", " "));
        assertEquals(params.get("timeEnd"), offer.getTimeEnd().replaceAll("T", " "));
        assertEquals(params.get("dateEnd"), offer.getDateEnd().replaceAll("T", " "));
        assertEquals(params.get("dateStart"), offer.getDateStart().replaceAll("T", " "));
        assertEquals(params.get("companyId"), offer.getCompany().getId());
        assertEquals(params.get("percentage"), offer.getPercentage());
        assertEquals(params.get("forMan"), offer.getForMan());
        assertEquals(params.get("forWoman"), offer.getForWoman());
        assertEquals(params.get("upperAgeLimit"), offer.getUpperAgeLimit());
        assertEquals(params.get("lowerAgeLimit"), offer.getLowerAgeLimit());
        assertTrue(offer.getImageId() > 0);
    }

    //GET /api/v{version}/Offer/top
    @Test
    public void getOfferTop() {
        ArrayList<OfferDTO> topOffers = getOfferTOP();

        if (topOffers.size() == 0)
            topOffers = getOfferTOPFromRemoteServer();

        assertTrue(topOffers.size() > 0);
        assertTrue(topOffers.size() <= 3);

        for (int i = 0; i < topOffers.size(); i++) {
            OfferDTO offer = topOffers.get(i);
        }
    }

    @Test
    public void getAllOffer() {
        createOffer();

        ArrayList<OfferDTO> list = Offer.getAllOffer();

        for (OfferDTO offer : list) {
            Date now = new Date();
            Date dateEnd = DateHelper.parse(offer.getDateEnd());
            Date timeEnd = DateHelper.parse(offer.getTimeEnd());

            assertTrue(dateEnd.after(now));
            assertTrue(timeEnd.after(now));
        }
    }

    @Test
    public void getOfferCategoryById() {
        ArrayList<OfferDTO> list;
        Integer id;

        do {
            id = ProductCategory.getRandomProdCat().getId();
            list = getOfferByCategory(id);
        } while (list.size() <= 0);

        for (OfferDTO offer : list) {
            assertEquals(offer.getCompany().getProductCategory().getId(), id);

            Date now = new Date();
            Date dateEnd = DateHelper.parse(offer.getDateEnd());
            Date timeEnd = DateHelper.parse(offer.getTimeEnd());

            assertTrue(dateEnd.after(now));
            assertTrue(timeEnd.after(now));
        }
    }

    @Test
    public void getOfferImage() {
        Integer id = getRandomOffer().getId();
        String image = Offer.getOfferImage(id);
        assertTrue(image.contains("JPEG") || image.contains("JFIF"));
    }

    @Test
    public void putOfferImageById() {
        OfferDTO offer = getRandomOffer();
        Integer id = offer.getId();
        File image = new File("src/test/java/resources/newOffer.jpeg");

        putOfferImage(id, image);
        Integer newImageId = getOffer(id).getImageId();

        assertNotEquals(offer.getImageId(), newImageId);
    }

    @Test
    public void postOfferLike() {
        Integer userId = createUser().getUser().getId();
        Integer offerId = createOffer().getId();

        Integer countBefore = getOffer(offerId).getLikeCounter();

        addLike(userId, offerId);
        countBefore++;

        Integer countAfter = getOffer(offerId).getLikeCounter();

        assertEquals(countBefore, countAfter);
    }

    @Test
    public void postOfferLike_BlockChek() {
        Integer userId = getRandomUser().getId();
        Integer offerId = getRandomOffer().getId();

        Integer countBefore = getOffer(offerId).getLikeCounter();

        addLike(userId, offerId);
        addLike(userId, offerId);
        countBefore++;

        Integer countAfter = getOffer(offerId).getLikeCounter();

        assertEquals(countBefore, countAfter);
    }

    @Test
    public void postOfferDisLike() {
        Integer userId = createUser().getUser().getId();
        Integer offerId = createOffer().getId();
        Integer countBefore = getOffer(offerId).getLikeCounter();
        Integer count = countBefore + 1;

        addLike(userId, offerId);

        assertEquals(count, getOffer(offerId).getLikeCounter());

        addDislike(userId, offerId);

        Integer countAfter = getOffer(offerId).getLikeCounter();

        assertEquals(countBefore, countAfter);
    }

    @Test
    public void inactiveOffer() {
        Integer companyId = getRandomCompany().getId();
        OfferDTO offer = createInactiveOffer(companyId);
        Integer offerId = offer.getId();

        CompanyOfferDTO companyOffer = getCompanyOffer(companyId);
        List<OfferDTO> list = companyOffer.getInactiveOffer();

        for (OfferDTO offerDTO : list) {
            if (offerDTO.getId().equals(offerId)) return;
        }
        fail();
    }

    @Test
    public void activeOffer() {
        Integer companyId = getRandomCompany().getId();
        OfferDTO offer = createActiveOffer(companyId);
        Integer offerId = offer.getId();

        CompanyOfferDTO companyOffer = getCompanyOffer(companyId);
        List<OfferDTO> list = companyOffer.getActiveOffer();

        for (OfferDTO offerDTO : list) {
            if (offerDTO.getId().equals(offerId)) return;
        }
        fail();
    }
}
