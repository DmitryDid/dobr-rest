package Tests;

import Action.Auth;
import Action.Company;
import Constants.CONST;
import DTO.AuthDTO;
import DTO.CompanyDTO;
import Helpers.DBHelpers;
import io.restassured.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthTest extends TestBase {

    // POST /api/v{version}/Auth/token/user/{playerId}
    @Test
    public void getTokenUserByPlayerId_200() {
        Response response = Auth.getTokenUserByPlayerId(CONST.PLAYER_ID);
        AuthDTO auth = response.as(AuthDTO.class);

        assertNotNull(auth.getAccessToken());
        assertEquals("bearer", auth.getTokenType());
        assertEquals("Success", auth.getStatus());
        assertEquals(CONST.PLAYER_ID, auth.getUser().getPlayerId());
    }

    // POST /api/v{version}/Auth/company
    @Test
    public void getCompanyTokenByUsernameAndPassword_200() {
        Integer companyId = 1;
        DBHelpers.confirmEmailCompanyById(companyId);

        CompanyDTO companyDTO = Company.getCompanyById(companyId).as(CompanyDTO.class);

        AuthDTO authDTO = Auth.getTokenCompanyByUsernameAndPassword(
                companyDTO.getEmail(), companyDTO.getPassword())
                .as(AuthDTO.class);

        assertNotNull(authDTO.getAccessToken());
        assertEquals("bearer", authDTO.getTokenType());
        assertEquals("Success", authDTO.getStatus());
        assertEquals(companyId, authDTO.getCompany().getId());
    }

    // POST /api/v{version}/Auth/token/company/{playerId}
    @Test
    public void getTokenForCompanyByCompanyId_200() {
        Integer companyId = 2;
        DBHelpers.confirmEmailCompanyById(companyId);

        CompanyDTO companyDTO = Company.getCompanyById(companyId).as(CompanyDTO.class);
        AuthDTO authDTO = Auth.getTokenForCompanyByPlayerId(companyDTO.getPlayerId()).as(AuthDTO.class);

        assertNotNull(authDTO.getAccessToken());
        assertEquals("bearer", authDTO.getTokenType());
        assertEquals("Success", authDTO.getStatus());
        assertEquals(companyId, authDTO.getCompany().getId());
    }

    // /api/v{version}/Auth/refresh-token
    @Test
    public void getRefreshToken_200() {
        AuthDTO authDTO = Auth.getTokenUserByPlayerId(CONST.PLAYER_ID).as(AuthDTO.class);
        String token = authDTO.getAccessToken();

        String refreshToken = Auth.getRefreshToken(token)
                .as(AuthDTO.class)
                .getAccessToken();

        assertNotNull(token);
        assertNotNull(refreshToken);
        assertNotEquals(token, refreshToken);
    }

    // POST /api/v{version}/Auth/company/lost-password
    @Test
    public void getCompanyLostPassword_200() {
        int companyId = 1;
        CompanyDTO companyDTO = Company.getCompanyById(companyId).as(CompanyDTO.class);
        DBHelpers.confirmEmailCompanyById(companyId);

        Response response = Auth.getCompanyLostPassword(companyDTO.getEmail(), Auth.getToken());
        //TODO: дописать
        toConsole(response);
    }

    // POST /api/v{version}/Auth/company/restore-password
    @Test
    public void getCompanyRestorePassword_200() {
        int companyId = 5;
        DBHelpers.confirmEmailCompanyById(companyId);

        CompanyDTO companyDTO = Company.getCompanyById(companyId).as(CompanyDTO.class);

        AuthDTO authDTO = Auth.getTokenCompanyByUsernameAndPassword(
                companyDTO.getEmail(), companyDTO.getPassword())
                .as(AuthDTO.class);
        //TODO: доработать когда заработает
        String newPassword = "NEW_test_company_password" + getUniqueNumber(3);

        AuthDTO newAuthDTO = Auth.getCompanyRestorePassword(CONST.EMAIL, newPassword,0000).as(AuthDTO.class);
        assertEquals("Success", newAuthDTO.getStatus());
        assertEquals(newPassword, newAuthDTO.getCompany().getPassword());
    }
}
