package advanced.integration.services.bookmarks.options;

import advanced.integration.config.ChangerTestConfig;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.AuthorizationType;
import game.mightywarriors.services.bookmarks.options.EmailChanger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class EmailChangerTest extends ChangerTestConfig {
    @Autowired
    private EmailChanger objectUnderTest;

    @Test
    public void prepareChangeEmail() throws Exception {

        objectUnderTest.prepareChangeEmail(token, informer);

        User user = userService.find(this.user.getId());
        assertEquals(1, user.getAuthorizationCodes().size());
        assertEquals(AuthorizationType.EMAIL, user.getAuthorizationCodes().iterator().next().getType());
    }

    @Test(expected = Exception.class)
    public void prepareChangePassword_login_already_taken() throws Exception {
        prepareChangeEmail();

        objectUnderTest.prepareChangeEmail(token, informer);
    }

    @Test
    public void changeEmail() throws Exception {
        prepareChangeEmail();
        informer.code = userService.find(user).getAuthorizationCodes().iterator().next().getAuthorizationCode();

        objectUnderTest.changeEmail(token, informer);

        user = userService.find(user);
        assertEquals(0, user.getAuthorizationCodes().size());
        assertEquals(newEmail, user.geteMail());
    }

    @Test(expected = Exception.class)
    public void prepareChangePassword_null_login() throws Exception {
        informer.email = null;

        objectUnderTest.prepareChangeEmail(token, informer);
    }

    @Test(expected = Exception.class)
    public void prepareChangePassword_existing_login() throws Exception {
        informer.email = "email@wp.pl";

        objectUnderTest.prepareChangeEmail(token, informer);
    }

    @Test(expected = Exception.class)
    public void prepareChangePassword_wrong_login() throws Exception {
        informer.email = "alkfhjksa";

        objectUnderTest.prepareChangeEmail(token, informer);
    }
}
