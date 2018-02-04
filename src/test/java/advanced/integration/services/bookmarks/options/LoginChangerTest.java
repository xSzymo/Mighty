package advanced.integration.services.bookmarks.options;

import advanced.integration.config.ChangerTestConfig;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.AuthorizationType;
import game.mightywarriors.services.bookmarks.options.LoginChanger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginChangerTest extends ChangerTestConfig {
    @Autowired
    private LoginChanger objectUnderTest;

    @Test
    public void prepareChangeLogin() throws Exception {

        objectUnderTest.prepareChangeLogin(token, informer);

        User user = userService.find(this.user.getId());
        assertEquals(1, user.getAuthorizationCodes().size());
        assertEquals(AuthorizationType.LOGIN, user.getAuthorizationCodes().iterator().next().getType());
    }

    @Test
    public void changeLogin() throws Exception {
        prepareChangeLogin();
        informer.code = userService.find(user).getAuthorizationCodes().iterator().next().getAuthorizationCode();

        objectUnderTest.changeLogin(token, informer);

        user = userService.find(user);
        assertTrue(user.getAuthorizationCodes().stream().anyMatch(x -> x.getType().getType().equals(AuthorizationType.LOGIN.getType())));
        assertEquals(newLogin, user.getLogin());
    }

    @Test(expected = Exception.class)
    public void prepareChangePassword_null_login() throws Exception {
        informer.login = null;

        objectUnderTest.prepareChangeLogin(token, informer);
    }

    @Test(expected = Exception.class)
    public void prepareChangePassword_existing_login() throws Exception {
        informer.login = "admin0";

        objectUnderTest.prepareChangeLogin(token, informer);
    }
}
