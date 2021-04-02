package Tests;

import Constants.CONST;
import DTO.AuthDTO;
import DTO.CompanyDTO;
import DTO.TopDTO;
import Helpers.DBHelpers;
import Pages.Company;
import Pages.User;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static Pages.Company.*;
import static Pages.ProductCategory.createProductCategory;
import static org.junit.Assert.*;

public class CompanyTest extends TestBase {

    @Test
    public void getCompanyById() {
        Map<String, Object> defaultParams = getDefaultParams();

        AuthDTO authDTO = Company.createCompany(defaultParams);

        int id = authDTO.getCompany().getId();

        CompanyDTO createCompany = Company.getCompanyById(id);

        assertEquals(id, createCompany.getId().toString());
        assertEquals(defaultParams.get("latitude"), createCompany.getLatitude());
        assertEquals(defaultParams.get("longitude"), createCompany.getLongitude());
        assertEquals(defaultParams.get("nameOfficial"), createCompany.getNameOfficial());
        assertEquals(defaultParams.get("representative"), createCompany.getRepresentative());
        assertEquals(defaultParams.get("password"), createCompany.getPassword());
        assertEquals(defaultParams.get("address"), createCompany.getAddress());
        assertEquals(defaultParams.get("timeOfWork"), createCompany.getTimeOfWork());
        assertEquals(defaultParams.get("playerId"), createCompany.getPlayerId());
        assertEquals(defaultParams.get("productCategoryId"), createCompany.getProductCategory().getId());
        assertEquals(defaultParams.get("phone"), createCompany.getPhone());
        assertEquals(defaultParams.get("inn"), createCompany.getInn());
        assertTrue(createCompany.getEmailConfirmed());
        assertNotNull(createCompany.getImageId()); //TODO: похоже бага
    }

    @Test
    public void putCompanyById() {
        AuthDTO authDTO = Company.createCompany(getDefaultParams());

        Integer id = authDTO.getCompany().getId();

        Integer productCategoryId = createProductCategory()
                .getId();

        Integer imageId = authDTO.getCompany().getImageId();

        Map<String, Object> map = new HashMap<>();
        map.put("productCategoryId", productCategoryId);
        map.put("name", "putCompanyById_newName");
        map.put("nameOfficial", "putCompanyById_nameOfficial");
        map.put("Latitude", 56.0183900);
        map.put("Longitude", 92.8671700);
        map.put("representative", "putCompanyById_representative");
        map.put("phone", getUniqueNumber(7));
        map.put("email", getUniqueNumber(8) + "@gmail.ru");
        map.put("inn", getUniqueNumber(9));
        map.put("password", getUniqueNumber(8));
        map.put("address", "putCompanyById_address");
        map.put("timeOfWork", "0-24");
        map.put("playerId", UUID.randomUUID().toString());

        updateCompanyById(id, map, new File("src/test/java/Resources/vtb.jpg"));

        CompanyDTO getCompanyDTO = Company.getCompanyById(id);

        assertEquals(map.get("productCategoryId"), getCompanyDTO.getProductCategory().getId());
        assertEquals(id, getCompanyDTO.getId().toString());
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
        assertTrue(getCompanyDTO.getEmailConfirmed());
        assertNotEquals(imageId, getCompanyDTO.getImageId());
        assertEquals(authDTO.getCompany().getNameOfficial(), getCompanyDTO.getNameOfficial());
    }

    @Test
    public void deleteCompanyById() {
        AuthDTO authDTO = Company.createCompany(getDefaultParams());

        int id = authDTO.getCompany().getId();

        Company.deleteCompanyById(id, 200);
        Company.deleteCompanyById(id, 404);
    }

    @Test
    public void getCompanyNotification() {
        fail();
    }

    @Test
    public void getCompanyTop() {
        ArrayList<TopDTO> companyList = Company.getCompanyTop();
        assertTrue(companyList.size() > 0);
    }


    // GET /api/v{version}/Company/{id}/number-of-favorites
    @Test
    public void getCompanyNumberOfFavorites() {
        int companyId = Company.createCompany(getDefaultParams()).getCompany().getId();
        int userId = User.createUser().getUser().getId();

        //  User.addedFavoriteForUser(userId,)


        ArrayList<CompanyDTO> list = Company.getAllCompany();
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < list.size(); i++) {
            //    int companyId = list.get(i).getId();
            int number = Company.getCompanyNumberOfFavorites(companyId);
            max = Math.max(max, number);
            System.out.println(number);
        }
        assertTrue(max > 0);
    }

    // POST /api/v{version}/Company
    @Test
    public void createCompany() {
        String email = getUniqueNumber(5) + "@yandex.com";

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
        assertFalse(companyDTO.getEmailConfirmed());
        assertTrue(companyDTO.getPlayerId().length() > 20);
        assertTrue(companyDTO.getProductCategory().getId() > 0);
        assertTrue(companyDTO.getImageId() > 0);
    }

    // /api/v{version}/Company
    @Test
    public void getCompanyAll() {
        ArrayList<CompanyDTO> companyList = getAllCompany();
        CompanyDTO currentCompany;

        for (int i = 0; i < companyList.size(); i++) {
            currentCompany = companyList.get(i);
            if (CONST.EMAIL.equals(currentCompany.getEmail())) {
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

    // GET /api/v{version}/Company/category/{id}
    @Test
    public void getCompanyCategoryById() {
        String id = DBHelpers.getId("SELECT * FROM company WHERE email = 'nsk_dem@mail.ru';");

        ArrayList companyList = getListCompanyDTO(Company.getCompanyCategoryById(id));

        assertTrue(companyList.size() > 0);
        CompanyDTO currentCompany = (CompanyDTO) companyList.get(0);

        assertEquals("1", currentCompany.getProductCategory().getId().toString());
        assertEquals("alcohol", currentCompany.getProductCategory().getName());
        assertEquals("18", currentCompany.getProductCategory().getAgeLimit().toString());
        assertEquals(null, currentCompany.getProductCategory().getImage());
    }


    @Test
    public void getCompanyImageById() {
        AuthDTO authDTO = Company.createCompany(getDefaultParams());

        int id = authDTO.getCompany().getId();

        Company.getCompanyImageById(id);
        System.out.println();
    }
}
