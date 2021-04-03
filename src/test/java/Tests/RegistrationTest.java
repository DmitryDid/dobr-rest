package Tests;

import Constants.CONST;
import Helpers.DBHelpers;
import Pages.Registration;
import org.junit.Test;

public class RegistrationTest extends TestBase {


    @Test
    public void emailConfirmation() {
        String email = CONST.EMAIL;
        Registration.emailConfirmation(email);
        String code = DBHelpers.getConfirmCode(CONST.EMAIL);
        Registration.emailAcceptance(email, code);
    }
}
