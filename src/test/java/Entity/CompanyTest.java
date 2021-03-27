package Entity;

import DTO.AuthDTO;
import io.restassured.response.Response;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.UUID;

public class CompanyTest extends TestCase {

    public void testGetAllCompany() {
        new Auth().getToken();
        Assert.assertEquals(200, new Company().getAllCompany().getStatusCode());
    }

    public void testGetCompanyById() {
        new Auth().getToken();
        Assert.assertEquals(200, new Company().getCompanyById(1).getStatusCode());
    }

    public void testCreateCompany() {
        new Auth().getToken();
        Assert.assertEquals(200, new Company().createCompany().getStatusCode());
    }

    public void testCreateCompanyWithEmail() {
        new Auth().getToken();
        String email = UUID.randomUUID().toString().substring(7) + "@mail.ru";
        Response response = new Company().createCompanyWithEmail(email);
        AuthDTO resultDTO = response.as(AuthDTO.class);

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(email, resultDTO.getCompany().getEmail());
    }
}