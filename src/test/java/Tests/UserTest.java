package Tests;

import DTO.AuthDTO;
import DTO.CompanyDTO;
import DTO.CompanyOfferDTO;
import DTO.UserDTO;
import Pages.Offer;
import Pages.User;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static Pages.Company.getRandomCompany;
import static Pages.User.*;
import static org.junit.Assert.*;

public class UserTest extends TestBase {

    @Test
    public void getUserImageById() {
        File image = new File("src/test/java/resources/MarinaC.jpeg");

        Map<String, Object> params = new HashMap<>();
        params.put("name", "Marina_" + getUniqueNumber(4));
        params.put("isMan", false);
        params.put("playerId", UUID.randomUUID().toString());
        params.put("birthYear", "1984-08-01T12:59:18.99");
        params.put("latitude", "56.0415000");
        params.put("longitude", "83.9346000");

        AuthDTO createdUser = createUser(params, image);
        int userId = createdUser.getUser().getId();
        AuthDTO receivedAuth = getUser(userId);
        UserDTO receivedUser = receivedAuth.getUser();

        assertEquals(params.get("name"), receivedUser.getName());
        assertEquals(params.get("isMan"), receivedUser.getIsMan());
        assertEquals(params.get("playerId"), receivedUser.getPlayerId());
        assertEquals(params.get("birthYear"), receivedUser.getBirthYear());
        assertTrue(params.get("latitude").toString().startsWith(receivedUser.getLatitude().toString()));
        assertTrue(params.get("longitude").toString().startsWith(receivedUser.getLongitude().toString()));
        assertTrue(receivedUser.getImageId() > 0);
        assertNull(receivedUser.getImage());

        assertEquals("Success", receivedAuth.getStatus());
        assertEquals("bearer", receivedAuth.getTokenType());
        assertTrue(receivedAuth.getAccessToken().length() > 100);
    }

    @Test
    public void putUserById() {
        File image = new File("src/test/java/resources/MarinaC.jpeg");

        Map<String, Object> createParams = new HashMap<>();
        createParams.put("name", "Marina_" + getUniqueNumber(4));
        createParams.put("isMan", false);
        createParams.put("playerId", UUID.randomUUID().toString());
        createParams.put("birthYear", "1984-08-01T12:59:18.99");
        createParams.put("latitude", "56.0415000");
        createParams.put("longitude", "83.9346000");

        AuthDTO created = createUser(createParams, image);
        Integer id = created.getUser().getId();
        Map<String, Object> updateParams = getDefaultUserParams();
        AuthDTO updated = updateUser(id, updateParams, IMAGE);

        assertEquals("Success", updated.getStatus());
        assertEquals(id, updated.getUser().getId());
        assertEquals(updateParams.get("name"), updated.getUser().getName());
        assertEquals(updateParams.get("isMan"), updated.getUser().getIsMan());
        assertEquals(updateParams.get("playerId"), updated.getUser().getPlayerId());
        assertEquals(updateParams.get("birthYear"), updated.getUser().getBirthYear());
        assertTrue(updateParams.get("latitude").toString().startsWith(updated.getUser().getLatitude().toString()));
        assertTrue(updateParams.get("longitude").toString().startsWith(updated.getUser().getLongitude().toString()));
    }

    // GET /api/v{version}/User/{id}/offer
    @Test
    public void getUserOffer() {
        int id = User.getRandomUser().getId();
        CompanyOfferDTO companyOffer = User.getUserOffer(id);
        System.out.println(id);

        assertTrue(companyOffer.getPreOffer().size() > 0);
        assertTrue(companyOffer.getInactiveOffer().size() > 0);
        assertTrue(companyOffer.getNearbyOffer().size() > 0);
        assertTrue(companyOffer.getActiveOffer().size() > 0);
    }

    // GET /api/v{version}/User/{id}/company/{companyId}/offer
    @Test
    public void getUserCompanyOffer() {
        fail();
    }

    // GET /api/v{version}/User/{id}/favarite-offer
    @Test
    public void getUserFavoriteOffer() {
        fail();
    }

    @Test
    public void getUsersAll() {
        File image = new File("src/test/java/resources/MarinaC.jpeg");

        Map<String, Object> params = new HashMap<>();
        params.put("name", "Marina_" + getUniqueNumber(4));
        params.put("isMan", false);
        params.put("playerId", UUID.randomUUID().toString());
        params.put("birthYear", "1984-08-01T12:59:18.99");
        params.put("latitude", "56.0415000");
        params.put("longitude", "83.9346000");

        AuthDTO createdUser = createUser(params, image);
        ArrayList<UserDTO> list = getAllUser();

        assertTrue(list.size() > 0);

        for (UserDTO current : list) {
            if (current.getId().equals(createdUser.getUser().getId())) {
                assertEquals(params.get("name"), current.getName());
                assertEquals(params.get("isMan"), current.getIsMan());
                assertEquals(params.get("playerId"), current.getPlayerId());
                assertEquals(params.get("birthYear"), current.getBirthYear());
                assertTrue(params.get("latitude").toString().startsWith(current.getLatitude().toString()));
                assertTrue(params.get("longitude").toString().startsWith(current.getLongitude().toString()));
                assertTrue(current.getImageId() > 0);
                assertNull(current.getImage());
            }
        }
    }

    @Test
    public void postUser() {
        File image = new File("src/test/java/resources/MarinaC.jpeg");

        Map<String, Object> params = new HashMap<>();
        params.put("name", "Marina_" + getUniqueNumber(4));
        params.put("isMan", false);
        params.put("playerId", UUID.randomUUID().toString());
        params.put("birthYear", "1984-08-01T12:59:18.99");
        params.put("latitude", "56.0415000");
        params.put("longitude", "83.9346000");

        AuthDTO user = createUser(params, image);

        assertEquals(params.get("name"), user.getUser().getName());
        assertEquals(params.get("isMan"), user.getUser().getIsMan());
        assertEquals(params.get("playerId"), user.getUser().getPlayerId());
        assertEquals(params.get("birthYear"), user.getUser().getBirthYear());
        assertTrue(params.get("latitude").toString().startsWith(user.getUser().getLatitude().toString()));
        assertTrue(params.get("longitude").toString().startsWith(user.getUser().getLongitude().toString()));

        assertEquals("Success", user.getStatus());
        assertEquals("bearer", user.getTokenType());
        assertTrue(user.getAccessToken().length() > 100);
    }

    // GET /api/v{version}/User/{id}/image
    @Test
    public void getUserImage() {
        fail();
    }

    // PUT /api/v{version}/User/{id}/image
    @Test
    public void putUserImage() {
        fail();
    }

    @Test
    public void getUserStories() {
        int id = getRandomUser().getId();
        ArrayList<UserDTO> listUser = User.getAllUser();

        int count = 0;
        for (UserDTO currentUser : listUser) {
            if (User.getStories(currentUser.getId()).size() > 0) count++;
        }
        assertTrue(count > 0);
    }

    // DELETE /api/v{version}/User/{id}/stories/company/{companyId}
    @Test
    public void deleteUserStoriesCompany() {
        fail();
    }

    // GET /api/v{version}/User/{userId}/company/{companyId}/distance
    @Test
    public void getUserCompanyDistance() {
        fail();
    }

    @Test
    public void getUserFavorite() {
        putUserFavorite();
    }

    @Test
    public void putUserFavorite() {
        Integer userId = getRandomUser().getId();
        Integer companyId = getRandomCompany().getId();

        addedFavoriteForUser(userId, companyId);
        ArrayList<CompanyDTO> listFavorite = User.getUserFavorite(userId);

        for (CompanyDTO current : listFavorite) {
            if (current.getId().equals(companyId)) return;
        }
        fail();
    }

    @Test
    public void deleteUserFavorite() {
        Integer userId = getRandomUser().getId();
        Integer companyId = getRandomCompany().getId();

        addedFavoriteForUser(userId, companyId);
        ArrayList<CompanyDTO> listFavorite = User.getUserFavorite(userId);

        int count = 0;
        for (CompanyDTO current : listFavorite) {
            if (current.getId().equals(companyId)) count++;
        }
        assertTrue(count > 0);

        deleteFavorite(userId, companyId);

        listFavorite = User.getUserFavorite(userId);

        for (CompanyDTO current : listFavorite) {
            if (current.getId().equals(count)) fail();
        }
    }
}
