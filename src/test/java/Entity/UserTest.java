package Entity;

import DTO.AuthDTO;
import io.restassured.response.Response;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.UUID;

public class UserTest extends TestCase {

    public void testCreateUser() {
        new Auth().getToken();
        Assert.assertEquals(200, new User().createUser().getStatusCode());
    }

    public void testTestCreateUser() {
        new Auth().getToken();
        String uuid = UUID.randomUUID().toString();
        Response response = new User().createUser(uuid);
        AuthDTO authDTO = response.as(AuthDTO.class);
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(uuid, authDTO.getUser().getPlayerId());
    }
}