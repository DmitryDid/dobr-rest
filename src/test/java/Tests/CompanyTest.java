package Tests;

import Constants.CONST;
import DTO.AuthDTO;
import DTO.CompanyDTO;
import DTO.FailDTO;
import Helpers.DBHelpers;
import Pages.Company;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Pages.Company.*;
import static org.junit.Assert.*;

public class CompanyTest extends TestBase {

    // GET /api/v{version}/Company/{id}
    @Test
    public void getCompanyById() {
        String id = DBHelpers.getId("SELECT * FROM company WHERE email = 'nsk_dem@mail.ru';");
        CompanyDTO dto = Company.getCompanyById(id).as(CompanyDTO.class);

        assertEquals(id, dto.getId().toString());
        assertEquals((Double) 55.0415, dto.getLatitude());
        assertEquals((Double) 82.9346, dto.getLongitude());
        assertEquals("test_company nameOfficial", dto.getNameOfficial());
        assertEquals("test_company_representative", dto.getRepresentative());
        assertTrue(Long.parseLong(dto.getPhone()) >= 0);
        assertTrue(Long.parseLong(dto.getInn()) >= 0);
        assertEquals("test_company_password", dto.getPassword());
        assertEquals("test_company_address", dto.getAddress());
        assertEquals("8-22", dto.getTimeOfWork());
        assertTrue(dto.getEmailConfirmed());
        assertTrue(dto.getPlayerId().length() > 20);
        assertEquals((Integer) 1, dto.getProductCategory().getId());
        assertEquals((Integer) 3, dto.getImageId());
        assertNull(dto.getImage()); //TODO: похоже бага
    }

    @Test
    public void putCompanyById() {
        String email = getUniqueNumber(5) + "@yandex.com";

        AuthDTO authDTO = createCompanyWithEmail(email)
                .as(AuthDTO.class);
        CompanyDTO companyDTO = authDTO.getCompany();
        String id = companyDTO.getId()
                .toString();

        Map<String, String> map = new HashMap<>();
        map.put("address", "updateAddress");
        map.put("nameOfficial", "updateNameNameOfficial");
        map.put("timeOfWork", "updateNameNameOfficial"); //TODO: bags
        map.put("productCategoryId", "1");

        updateCompanyById(id, map);

        CompanyDTO getCompanyDTO = Company.getCompanyById(id)
                .as(CompanyDTO.class);

        assertEquals(map.get("address"), getCompanyDTO.getAddress());
        assertEquals(map.get("nameOfficial"), getCompanyDTO.getNameOfficial());
        assertEquals(map.get("timeOfWork"), getCompanyDTO.getTimeOfWork());
        assertEquals(map.get("productCategoryId"), getCompanyDTO.getProductCategory().getId().toString());
    }

    // DELETE /api/v{version}/Company/{id}
    @Test
    public void deleteCompanyById() {
        String email = getUniqueNumber(5) + "@yandex.com";

        AuthDTO authDTO = createCompanyWithEmail(email).as(AuthDTO.class); //TODO: ломаемся, если прогон в группе
        String companyId = authDTO.getCompany().getId().toString();
        Company.deleteCompanyById(companyId).as(CompanyDTO.class);
        FailDTO failDTO = Company.getCompanyById(companyId).as(FailDTO.class);
        Assert.assertEquals("COMPANY_ERROR", failDTO.getStatus());
        Assert.assertEquals("Не найдена компания с id = " + companyId, failDTO.getMessage());
    }

    // POST /api/v{version}/Company
    @Test
    public void createCompany() {
        String email = getUniqueNumber(5) + "@yandex.com";

        AuthDTO authDTO = createCompanyWithEmail(email)
                .as(AuthDTO.class);
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
        ArrayList companyList = getListCompanyDTO(getAllCompany());
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

    // GET /api/v{version}/Company/top
    @Test
    public void getCompanyTop() {
        ArrayList companyList = getListCompanyDTO(Company.getCompanyTop());

        assertTrue(companyList.size() > 0);
        fail();
    }

    @Test
    public void getCompanyImageById() {
        CompanyDTO companyDTO = Company.createCompany().as(CompanyDTO.class);
        String id = companyDTO.getId().toString();
        Company.getCompanyImageById(id);
        System.out.println();
    }
}
