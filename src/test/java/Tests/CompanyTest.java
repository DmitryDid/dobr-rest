package Tests;

import DTO.*;
import Pages.Company;
import Pages.Offer;
import Pages.User;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static Pages.Company.*;
import static Pages.ProductCategory.createProductCategory;
import static Pages.ProductCategory.getRandomProdCat;
import static Pages.User.*;
import static org.junit.Assert.*;

public class CompanyTest extends TestBase {

    @Test
    public void postCompany() {
        String email = getUniqueNumber(6) + "@list.ru";

        AuthDTO authDTO = createCompanyWithEmail(email);
        CompanyDTO companyDTO = authDTO.getCompany();

        assertEquals(email, companyDTO.getEmail());
        assertNotNull(companyDTO.getId());
        assertEquals((Double) 55.0415, companyDTO.getLatitude());
        assertEquals((Double) 82.9346, companyDTO.getLongitude());
        assertEquals("test_company nameOfficial", companyDTO.getNameOfficial());
        assertEquals("test_company_representative", companyDTO.getRepresentative());
        assertTrue(Long.parseLong(companyDTO.getPhone()) >= 0);
        assertTrue(Long.parseLong(companyDTO.getInn()) >= 0);
        assertEquals("test_company_password", companyDTO.getPassword());
        assertEquals("test_company_address", companyDTO.getAddress());
        assertEquals("8-22", companyDTO.getTimeOfWork());
        assertEquals("Asia/Novosibirsk", companyDTO.getTimeZone());
        assertTrue(companyDTO.getEmailConfirmed());
        assertTrue(companyDTO.getPlayerId().length() > 20);
        assertTrue(companyDTO.getProductCategory().getId() > 0);
        assertTrue(companyDTO.getImageId() > 0);
    }

    @Test
    public void postCompany_timeZone() {
        Map<String, Object> params = new HashMap<>();
        params.put("latitude", 56.0183900);
        params.put("longitude", 92.8671700);

        CompanyDTO companyDTO = createCompany(params).getCompany();
        assertEquals("Asia/Krasnoyarsk", companyDTO.getTimeZone());
    }

    @Test
    public void postCompany_timeZone_barabinsk() {
        Map<String, Object> params = new HashMap<>();
        params.put("latitude", 55.3570900);
        params.put("longitude", 78.3569700);

        CompanyDTO companyDTO = createCompany(params).getCompany();
        assertEquals("Asia/Novosibirsk", companyDTO.getTimeZone());
    }

    @Test
    public void postCompany_timeZone_tumen() {
        Map<String, Object> params = new HashMap<>();
        params.put("latitude", 57.1522200);
        params.put("longitude", 65.5272200);

        CompanyDTO companyDTO = createCompany(params).getCompany();
        assertEquals("Asia/Yekaterinburg", companyDTO.getTimeZone());
    }

    @Test
    public void postCompany_default_timeZone() {
        CompanyDTO company = createCompany().getCompany();
        assertEquals("Asia/Novosibirsk", company.getTimeZone());
    }

    @Test
    public void putCompanyById() {
        AuthDTO authDTO = createCompany();

        Integer id = authDTO.getCompany().getId();

        Integer productCategoryId = createProductCategory()
                .getId();

        Integer imageId = authDTO.getCompany().getImageId();

        Map<String, Object> map = new HashMap<>();
        map.put("productCategoryId", productCategoryId);
        map.put("name", "putCompanyById_newName");
        map.put("nameOfficial", "putCompanyById_nameOfficial");
        map.put("Latitude", 55.7522200);
        map.put("Longitude", 37.6155600);
        map.put("representative", "putCompanyById_representative");
        map.put("phone", getUniqueNumber(7));
        map.put("email", getUniqueNumber(8) + "@gmail.ru");
        map.put("inn", getUniqueNumber(9));
        map.put("password", getUniqueNumber(8));
        map.put("address", "putCompanyById_address");
        map.put("timeOfWork", "0-24");
        map.put("playerId", UUID.randomUUID().toString());

        updateCompany(id, map, new File("src/test/java/resources/vtb.jpg"));

        CompanyDTO getCompanyDTO = getCompany(id);

        assertEquals(map.get("productCategoryId"), getCompanyDTO.getProductCategory().getId());
        assertEquals(id, getCompanyDTO.getId());
        assertEquals(map.get("Latitude"), getCompanyDTO.getLatitude());
        assertEquals(map.get("Longitude"), getCompanyDTO.getLongitude());
        assertEquals(map.get("representative"), getCompanyDTO.getRepresentative());
        assertEquals(map.get("password"), getCompanyDTO.getPassword());
        assertEquals(map.get("address"), getCompanyDTO.getAddress());
        assertEquals(map.get("timeOfWork"), getCompanyDTO.getTimeOfWork());
        assertEquals(map.get("playerId"), getCompanyDTO.getPlayerId());
        assertEquals(map.get("productCategoryId"), getCompanyDTO.getProductCategory().getId());
        assertEquals(map.get("phone"), getCompanyDTO.getPhone());
        assertEquals(map.get("inn"), getCompanyDTO.getInn());
        assertEquals("Asia/Novosibirsk", getCompanyDTO.getTimeZone());
        assertTrue(getCompanyDTO.getEmailConfirmed());
        assertNotEquals(imageId, getCompanyDTO.getImageId());
        assertEquals(authDTO.getCompany().getNameOfficial(), getCompanyDTO.getNameOfficial());
    }

    @Test
    public void putCompanyImage() {
        AuthDTO company = createCompany();
        Integer companyId = company.getCompany().getId();
        Integer oldImageId = company.getCompany().getImageId();

        updateCompanyImage(companyId, new File("src/test/java/resources/Kiprensky_Pushkin_2.jpg"));

        CompanyDTO updateCompany = getCompany(companyId);
        Integer newImageId = updateCompany.getImageId();

        assertEquals(company.getCompany().getId(), updateCompany.getId());
        assertNotEquals(oldImageId, newImageId);
    }

    @Test
    public void getCompanyById() {
        Map<String, Object> map = getDefaultParams();

        Integer companyId = createCompany(map).getCompany().getId();

        CompanyDTO getCompany = getCompany(companyId);

        assertEquals(companyId, getCompany.getId());
        assertEquals(map.get("latitude"), getCompany.getLatitude());
        assertEquals(map.get("longitude"), getCompany.getLongitude());
        assertEquals(map.get("nameOfficial"), getCompany.getNameOfficial());
        assertEquals(map.get("representative"), getCompany.getRepresentative());
        assertEquals(map.get("password"), getCompany.getPassword());
        assertEquals(map.get("address"), getCompany.getAddress());
        assertEquals(map.get("timeOfWork"), getCompany.getTimeOfWork());
        assertEquals(map.get("playerId"), getCompany.getPlayerId());
        assertEquals(map.get("productCategoryId"), getCompany.getProductCategory().getId());
        assertEquals(map.get("phone"), getCompany.getPhone());
        assertEquals(map.get("inn"), getCompany.getInn());
        assertEquals(map.get("email"), getCompany.getEmail());
        assertEquals("Asia/Novosibirsk", getCompany.getTimeZone());
        assertTrue(getCompany.getEmailConfirmed());
        assertNotNull(getCompany.getImageId());
    }

    @Test
    public void getCompanyNotification() {
        UserDTO userDTO = getRandomUser();
        ProductCategoryDTO productCategoryDTO = getRandomProdCat();
        addedFavoriteForUser(userDTO.getId(), productCategoryDTO.getId());



        Company.getCompanyNotification(1);
        // []
        fail();
    }

    @Test
    public void getCompanyTop() {
        UserDTO userDTO = getRandomUser();
        ProductCategoryDTO productCategoryDTO = getRandomProdCat();

        addedFavoriteForUser(userDTO.getId(), productCategoryDTO.getId());

        ArrayList<TopDTO> list = Company.getCompanyTop();

        assertTrue(list.size() > 0);
        for (int i = 0; i < list.size(); i++) {
            assertTrue(list.get(i).getCount() > 0);
        }
    }

    @Test
    public void getCompanyNumberOfFavorites() {
        int companyId = createCompany().getCompany().getId();
        int userId = createUser().getUser().getId();

        User.addedFavoriteForUser(userId, companyId);

        ArrayList<CompanyDTO> list = getAllCompany();
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < list.size(); i++) {
            int number = Company.getCompanyNumberOfFavorites(companyId);
            max = Math.max(max, number);
        }
        assertTrue(max > 0);
    }

    @Test
    public void getCompanyOfferLimit() {
        fail();
    }

    @Test
    public void getCompanyImage() {
        AuthDTO authDTO = createCompany();

        int id = authDTO.getCompany().getId();

        Company.getCompanyImage(id);
        System.out.println();
    }

    @Test
    public void getCompanyOffer() {
        int companyId = Company.getRandomCompany().getId();
        Map<String, Object> params = Offer.getDefaultOfferParams();
        params.put("companyId", companyId);

        Offer.createOffer(params);
        CompanyOfferDTO companyOffer = Company.getCompanyOffer(companyId);
        OfferDTO inactiveOffer = companyOffer.getInactiveOffer().get(0);

        assertTrue(inactiveOffer.getId() > 0);
        assertEquals(0, (int) inactiveOffer.getLikeCounter());
        assertEquals(params.get("text"), inactiveOffer.getText());
        assertNotNull(inactiveOffer.getCreateDate());
        assertEquals(params.get("sendingTime"), inactiveOffer.getSendingTime().replaceAll("T", " "));
        assertEquals(params.get("dateStart"), inactiveOffer.getDateStart().replaceAll("T", " "));
        assertEquals(params.get("dateEnd"), inactiveOffer.getDateEnd().replaceAll("T", " "));
        assertEquals(params.get("timeEnd"), inactiveOffer.getTimeEnd().replaceAll("T", " "));
        assertEquals(params.get("companyId"), inactiveOffer.getCompany().getId());
        assertEquals(params.get("percentage"), inactiveOffer.getPercentage());
        assertEquals(params.get("forMan"), inactiveOffer.getForMan());
        assertEquals(params.get("forWoman"), inactiveOffer.getForWoman());
        assertEquals(params.get("upperAgeLimit"), inactiveOffer.getUpperAgeLimit());
        assertEquals(params.get("lowerAgeLimit"), inactiveOffer.getLowerAgeLimit());
        assertEquals(inactiveOffer.getUserLike(), false);
    }

    @Test
    public void getCompanyCategory() {
        Integer productCategoryId = 1;

        ArrayList<CompanyDTO> list = getListCompanyCategory(productCategoryId);

        assertTrue(list.size() > 0);

        for (int i = 0; i < list.size(); i++) {
            Integer currentId = list.get(i).getProductCategory().getId();
            assertEquals(productCategoryId, currentId);
        }
    }

    @Test
    public void getCompanyAll() {
        ArrayList<CompanyDTO> companyList = getAllCompany();
        CompanyDTO currentCompany;

        for (int i = 0; i < companyList.size(); i++) {
            currentCompany = companyList.get(i);
            if (EMAIL.equals(currentCompany.getEmail())) {
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
                assertNotNull(currentCompany.getImageId());
                return;
            }
        }
        fail();
    }

    @Test
    public void deleteCompanyById() {
        AuthDTO authDTO = createCompany();

        int id = authDTO.getCompany().getId();

        deleteCompany(id, 200);
        deleteCompany(id, 404);
    }

    @Test
    public void deleteCompanyCascade() {
        CompanyDTO company = Company.createCompany().getCompany();
        int companyId = company.getId();

        Company.deleteCompanyCascade(companyId, 200);
        Company.deleteCompanyCascade(companyId, 404);
        //TODO: добавить проверки на каскадное удаление
    }
}
