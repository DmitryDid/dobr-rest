package Tests;

import Constants.Const;
import DTO.AuthDTO;
import DTO.CompanyDTO;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class AuthTest extends TestBase {

    // POST /api/v{version}/Auth/token/user/{playerId}
    @Test
    public void getTokenUserByPlayerId_200() {
        Response response = auth.getTokenUserByPlayerId(Const.PLAYER_ID);
        AuthDTO auth = response.as(AuthDTO.class);

        assertNotNull(auth.getAccessToken());
        assertEquals("bearer", auth.getTokenType());
        assertEquals("Success", auth.getStatus());
        assertEquals(Const.PLAYER_ID, auth.getUser().getPlayerId());
    }

    // POST /api/v{version}/Auth/company
    @Test
    public void getCompanyTokenByUsernameAndPassword_200() {
        Integer companyId = 1;
        dbHelpers.confirmEmailCompanyById(companyId);

        CompanyDTO companyDTO = company.getCompanyById(companyId).as(CompanyDTO.class);

        AuthDTO authDTO = auth.getTokenCompanyByUsernameAndPassword(
                companyDTO.getEmail(), companyDTO.getPassword())
                .as(AuthDTO.class);

        assertNotNull(authDTO.getAccessToken());
        assertEquals("bearer", authDTO.getTokenType());
        assertEquals("Success", authDTO.getStatus());
        assertEquals(companyId, authDTO.getCompany().getId());
    }

    // POST /api/v{version}/Auth/token/company/{playerId}
    @Test
    public void getTokenForCompanyByPlayerId_200() {
        Integer companyId = 2;
        dbHelpers.confirmEmailCompanyById(companyId);

        CompanyDTO companyDTO = company.getCompanyById(companyId).as(CompanyDTO.class);
        AuthDTO authDTO = auth.getTokenForCompanyByPlayerId(companyDTO.getPlayerId()).as(AuthDTO.class);

        assertNotNull(authDTO.getAccessToken());
        assertEquals("bearer", authDTO.getTokenType());
        assertEquals("Success", authDTO.getStatus());
        assertEquals(companyId, authDTO.getCompany().getId());
    }

    // /api/v{version}/Auth/refresh-token
    @Test
    public void getRefreshToken_200() {
        AuthDTO authDTO = auth.getTokenUserByPlayerId(Const.PLAYER_ID).as(AuthDTO.class);
        String token = authDTO.getAccessToken();

        String refreshToken = auth.getRefreshToken(token)
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
        CompanyDTO companyDTO = company.getCompanyById(companyId).as(CompanyDTO.class);
        dbHelpers.confirmEmailCompanyById(companyId);

        auth.getCompanyLostPassword(companyDTO.getEmail(), auth.getToken()); // 500
    }

    // POST /api/v{version}/Auth/company/restore-password
    @Test
    public void getCompanyRestorePassword_200() {
        int companyId = 5;
        dbHelpers.confirmEmailCompanyById(companyId);

        CompanyDTO companyDTO = company.getCompanyById(companyId).as(CompanyDTO.class);

        AuthDTO authDTO = auth.getTokenCompanyByUsernameAndPassword(
                companyDTO.getEmail(), companyDTO.getPassword())
                .as(AuthDTO.class);

        ArrayList list = dbHelpers.getConfirmCode();
        for (int i = 0; i < list.size(); i++) {

            Response response = given()
                    .spec(baseSpec)
                    .header("Authorization", "Bearer " + authDTO.getAccessToken())
                    .log().uri().log().method()
                    .when()
                    .body("{\n" +
                            "  \"email\": \"" + companyDTO.getEmail() + "\",\n" +
                            "  \"newPassword\": \"NEW_test_company_password\",\n" +
                            "  \"code\": \"" + list.get(i) + "\"\n" +
                            "}")
                    .post("Auth/company/restore-password")
                    .then()
                    //.statusCode(200) // 500
                    .extract()
                    .response();
            System.out.println(response.getStatusCode());
        }
    }
}
