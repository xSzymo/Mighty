package advanced.integration.services.bookmarks.options;

import advanced.integration.config.ChangerTestConfig;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.AuthorizationType;
import game.mightywarriors.services.bookmarks.options.PasswordChanger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class PasswordChangerTest extends ChangerTestConfig {
    @Autowired
    private PasswordChanger objectUnderTest;

    @Test
    public void prepareChangePassword() throws Exception {

        objectUnderTest.prepareChangePassword(token, informer);

        User user = userService.find(this.user.getId());
        assertEquals(1, user.getAuthorizationCodes().size());
        assertEquals(AuthorizationType.PASSWORD, user.getAuthorizationCodes().iterator().next().getType());
    }

    @Test
    public void changePassword() throws Exception {
        prepareChangePassword();
        informer.code = userService.find(user).getAuthorizationCodes().iterator().next().getAuthorizationCode();

        objectUnderTest.changePassword(token, informer);

        user = userService.find(user);
        assertEquals(0, user.getAuthorizationCodes().size());
        assertEquals(newPassword, user.getPassword());
    }
}
