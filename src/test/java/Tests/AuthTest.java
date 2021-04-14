package Tests;

import DTO.AuthDTO;
import DTO.CompanyDTO;
import Pages.Auth;
import Pages.Company;
import org.junit.Ignore;
import org.junit.Test;

import static Pages.Auth.*;
import static Pages.Company.*;
import static org.junit.Assert.*;

public class AuthTest extends TestBase {

    // POST /api/v{version}/Auth/token/user/{playerId}
    @Test
    public void getTokenUserByPlayerId() {
        AuthDTO auth = Auth.getTokenUserByPlayerId(PLAYER_ID);

        assertNotNull(auth.getAccessToken());
        assertEquals("bearer", auth.getTokenType());
        assertEquals("Success", auth.getStatus());
        assertEquals(PLAYER_ID, auth.getUser().getPlayerId());
    }

    // POST /api/v{version}/Auth/company
    @Test
    public void getCompanyTokenByUsernameAndPassword() {
        Integer companyId = createCompany()
                .getCompany().getId();

        CompanyDTO companyDTO = getCompany(companyId);

        AuthDTO authDTO = getTokenCompanyByUsernameAndPassword(companyDTO.getEmail(), companyDTO.getPassword());

        assertNotNull(authDTO.getAccessToken());
        assertEquals("bearer", authDTO.getTokenType());
        assertEquals("Success", authDTO.getStatus());
        assertEquals(companyId, authDTO.getCompany().getId());
    }

    // POST /api/v{version}/Auth/token/company/{playerId}
    @Test
    public void getTokenForCompanyByCompanyId() throws InterruptedException {
        Integer companyId = createCompany()
                .getCompany().getId();

        CompanyDTO companyDTO = getCompany(companyId);

        AuthDTO authDTO = getTokenForCompanyByPlayerId(companyDTO.getPlayerId());

        assertNotNull(authDTO.getAccessToken());
        assertEquals("bearer", authDTO.getTokenType());
        assertEquals("Success", authDTO.getStatus());
        assertEquals(companyId, authDTO.getCompany().getId());
    }

    // /api/v{version}/Auth/refresh-token
    @Test
    public void getRefreshToken() {
        String token = Auth.getTokenUserByPlayerId(PLAYER_ID)
                .getAccessToken();

        String refreshToken = Auth.getRefreshToken(token)
                .getAccessToken();

        assertNotNull(token);
        assertNotNull(refreshToken);
        assertNotEquals(token, refreshToken);
    }

    // POST /api/v{version}/Auth/company/lost-password
    @Ignore
    @Test
    public void getCompanyLostPassword() {
        String email = getUniqueNumber(8) + "_nsk_dem@mail.ru";
        Company.createCompanyWithEmail(email);

        Auth.getCompanyLostPassword(email, getToken());
        //TODO: ХЗ
    }

    // POST /api/v{version}/Auth/company/restore-password
    @Ignore
    @Test
    public void getCompanyRestorePassword() {
        int companyId = createCompany(getDefaultParams())
                .getCompany().getId();

        CompanyDTO companyDTO = getCompany(companyId);

        AuthDTO authDTO = getTokenCompanyByUsernameAndPassword(
                companyDTO.getEmail(), companyDTO.getPassword());

        String newPassword = "NEW_test_company_password" + getUniqueNumber(3);

        AuthDTO newAuthDTO = Auth.getCompanyRestorePassword(EMAIL, newPassword, 0000);   //TODO: доработать когда заработает
        assertEquals("Success", newAuthDTO.getStatus());
        assertEquals(newPassword, newAuthDTO.getCompany().getPassword());
    }
}
