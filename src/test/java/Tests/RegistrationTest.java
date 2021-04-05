package Tests;

import Helpers.DBHelpers;
import Pages.Registration;
import org.junit.Test;

public class RegistrationTest extends TestBase {


    @Test
    public void emailConfirmation() {
        String email = EMAIL;
        Registration.emailConfirmation(email);
        String code = DBHelpers.getConfirmCode(EMAIL);
        Registration.emailAcceptance(email, code);
    }
}
