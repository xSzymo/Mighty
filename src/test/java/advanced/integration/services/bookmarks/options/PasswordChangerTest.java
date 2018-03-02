package advanced.integration.services.bookmarks.options;

import advanced.integration.config.ChangerTestConfig;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.AuthorizationType;
import game.mightywarriors.services.bookmarks.options.PasswordChanger;
import game.mightywarriors.web.json.objects.bookmarks.CodeInformer;
import game.mightywarriors.web.json.objects.bookmarks.PasswordInformer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PasswordChangerTest extends ChangerTestConfig {
    @Autowired
    private PasswordChanger objectUnderTest;

    @Test
    public void prepareChangePassword() throws Exception {

        objectUnderTest.prepareChangePassword(token, new PasswordInformer(informer.password, informer.code));

        User user = userService.find(this.user.getId());
        assertEquals(1, user.getAuthorizationCodes().size());
        assertEquals(AuthorizationType.PASSWORD, user.getAuthorizationCodes().iterator().next().getType());
    }

    @Test
    public void changePassword() throws Exception {
        prepareChangePassword();
        informer.code = userService.find(user).getAuthorizationCodes().iterator().next().getAuthorizationCode();

        objectUnderTest.changePassword(token, new CodeInformer(informer.code));

        user = userService.find(user);
        assertTrue(user.getAuthorizationCodes().stream().anyMatch(x -> x.getType().getType().equals(AuthorizationType.PASSWORD.getType())));
        assertEquals(newPassword, user.getPassword());
    }
}
