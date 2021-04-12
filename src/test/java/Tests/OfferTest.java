package Tests;

import DTO.CompanyOfferDTO;
import DTO.OfferDTO;
import DTO.UserDTO;
import Helpers.DateHelper;
import Pages.Company;
import Pages.Offer;
import Pages.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static Pages.Company.getCompanyOffer;
import static Pages.Company.getRandomCompany;
import static Pages.Offer.*;
import static org.junit.Assert.*;

public class OfferTest extends TestBase {

    //POST /api/v{version}/Offer
    @Test
    public void postOffer() {
        Map<String, Object> params = getDefaultOfferParams();
        OfferDTO offerDTO = Offer.createOffer(params);

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
        assertTrue(offerDTO.getImageId() > 0);
    }

    //GET /api/v{version}/Offer/{id}
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
        Integer companyId = Company.getRandomCompany().getId();
        Integer offerId = Offer.createInactiveOffer(companyId).getId();
        ArrayList<UserDTO> listUsers = User.getAllUser();

        for (UserDTO listUser : listUsers) {
            addLike(listUser.getId(), offerId);
        }
        ArrayList<OfferDTO> topOffers = getOfferTOP();
        Assert.assertTrue(topOffers.size() > 0);
    }

    /*
    Только будующие офферы
     */
    @Test
    public void getAllOffer() {
        Offer.createOffer();

        ArrayList<OfferDTO> list = Offer.getAllOffer();

        for (OfferDTO offer : list) {
            Date now = new Date();
            Date dateStart = DateHelper.parse(offer.getDateStart());
            Date dateEnd = DateHelper.parse(offer.getDateEnd());
            Date timeStart = DateHelper.parse(offer.getTimeStart());
            Date timeEnd = DateHelper.parse(offer.getTimeEnd());

            assertTrue(dateEnd.after(now));
            assertTrue(timeEnd.after(now));
        }
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
        Integer OfferId = getRandomOffer().getId();
        addLike(userId, OfferId);
        addLike(userId, OfferId);
        addLike(userId, OfferId);

    }

    //POST /api/v{version}/Offer/dislike
    @Test
    public void postOfferDisLike() {
        Integer userId = User.getRandomUser().getId();
        Integer OfferId = getRandomOffer().getId();
        addDislike(userId, OfferId);
        addDislike(userId, OfferId);
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

    @Test
    public void nearbyOffer() {
        fail();
    }

    @Test
    public void preOffer() {
        fail();
    }
}
