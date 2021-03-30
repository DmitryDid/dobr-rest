package Tests;

import Constants.CONST;
import DTO.AuthDTO;
import DTO.CompanyDTO;
import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.Test;

import static Helpers.DBHelpers.confirmEmailCompanyById;
import static Pages.Auth.*;
import static Pages.Company.createCompany;
import static Pages.Company.getCompanyById;
import static Pages.ProductCategory.createProductCategory;
import static org.junit.Assert.*;

public class AuthTest extends TestBase {

    // POST /api/v{version}/Auth/token/user/{playerId}
    @Test
    public void getTokenUserByPlayerId_200() {
        Response response = getTokenUserByPlayerId(CONST.PLAYER_ID);
        AuthDTO auth = response.as(AuthDTO.class);

        assertNotNull(auth.getAccessToken());
        assertEquals("bearer", auth.getTokenType());
        assertEquals("Success", auth.getStatus());
        assertEquals(CONST.PLAYER_ID, auth.getUser().getPlayerId());
    }

    // POST /api/v{version}/Auth/company
    @Test
    public void getCompanyTokenByUsernameAndPassword_200() {
        createProductCategory();

        String companyId = createCompany()
                .as(AuthDTO.class)
                .getCompany().getId().toString();

        confirmEmailCompanyById(companyId);

        CompanyDTO companyDTO = getCompanyById(companyId)
                .as(CompanyDTO.class);

        AuthDTO authDTO = getTokenCompanyByUsernameAndPassword(companyDTO.getEmail(), companyDTO.getPassword())
                .as(AuthDTO.class);

        assertNotNull(authDTO.getAccessToken());
        assertEquals("bearer", authDTO.getTokenType());
        assertEquals("Success", authDTO.getStatus());
        assertEquals(companyId, authDTO.getCompany().getId().toString());
    }

    // POST /api/v{version}/Auth/token/company/{playerId}
    @Test
    public void getTokenForCompanyByCompanyId_200() throws InterruptedException {
        String companyId = createCompany()
                .as(AuthDTO.class)
                .getCompany().getId().toString();

        confirmEmailCompanyById(companyId);

        CompanyDTO companyDTO = getCompanyById(companyId)
                .as(CompanyDTO.class);

        AuthDTO authDTO = getTokenForCompanyByPlayerId(companyDTO.getPlayerId()).as(AuthDTO.class);

        assertNotNull(authDTO.getAccessToken());
        assertEquals("bearer", authDTO.getTokenType());
        assertEquals("Success", authDTO.getStatus());
        assertEquals(companyId, authDTO.getCompany().getId().toString());
    }

    // /api/v{version}/Auth/refresh-token
    @Test
    public void getRefreshToken_200() {
        AuthDTO authDTO = getTokenUserByPlayerId(CONST.PLAYER_ID)
                .as(AuthDTO.class);

        String token = authDTO.getAccessToken();

        String refreshToken = getRefreshToken(token)
                .as(AuthDTO.class)
                .getAccessToken();

        assertNotNull(token);
        assertNotNull(refreshToken);
        assertNotEquals(token, refreshToken);
    }

    // POST /api/v{version}/Auth/company/lost-password
    @Ignore
    @Test
    public void getCompanyLostPassword_200() {
        String companyId = "1";
        CompanyDTO companyDTO = getCompanyById(companyId).as(CompanyDTO.class);
        confirmEmailCompanyById(companyId);

        Response response = getCompanyLostPassword(companyDTO.getEmail(), getToken());
        //TODO: дописать
        toConsole(response);
    }

    // POST /api/v{version}/Auth/company/restore-password
    @Ignore
    @Test
    public void getCompanyRestorePassword_200() {
        String companyId = createCompany()
                .as(AuthDTO.class)
                .getCompany().getId().toString();

        confirmEmailCompanyById(companyId);

        CompanyDTO companyDTO = getCompanyById(companyId).as(CompanyDTO.class);

        AuthDTO authDTO = getTokenCompanyByUsernameAndPassword(
                companyDTO.getEmail(), companyDTO.getPassword())
                .as(AuthDTO.class);

        String newPassword = "NEW_test_company_password" + getUniqueNumber(3);

        AuthDTO newAuthDTO = getCompanyRestorePassword(CONST.EMAIL, newPassword, 0000).as(AuthDTO.class);   //TODO: доработать когда заработает
        assertEquals("Success", newAuthDTO.getStatus());
        assertEquals(newPassword, newAuthDTO.getCompany().getPassword());
    }
}
