package advanced.integration.services.bookmarks.options;

import advanced.integration.config.ChangerTestConfig;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.AuthorizationType;
import game.mightywarriors.services.bookmarks.options.EmailChanger;
import game.mightywarriors.web.json.objects.bookmarks.CodeInformer;
import game.mightywarriors.web.json.objects.bookmarks.RemindInformer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EmailChangerTest extends ChangerTestConfig {
    @Autowired
    private EmailChanger objectUnderTest;

    @Test
    public void prepareChangeEmail() throws Exception {

        objectUnderTest.prepareChangeEmail(token, new RemindInformer(informer.email));

        User user = userService.find(this.user.getId());
        assertEquals(1, user.getAuthorizationCodes().size());
        assertEquals(AuthorizationType.EMAIL, user.getAuthorizationCodes().iterator().next().getType());
    }

    @Test(expected = Exception.class)
    public void prepareChangePassword_login_already_taken() throws Exception {
        prepareChangeEmail();

        objectUnderTest.prepareChangeEmail(token, new RemindInformer(informer.email));
    }

    @Test
    public void changeEmail() throws Exception {
        prepareChangeEmail();
        informer.code = userService.find(user).getAuthorizationCodes().iterator().next().getAuthorizationCode();

        objectUnderTest.changeEmail(token, new CodeInformer(informer.code));

        user = userService.find(user);
        assertTrue(user.getAuthorizationCodes().stream().anyMatch(x -> x.getType().getType().equals(AuthorizationType.EMAIL.getType())));
        assertEquals(newEmail, user.geteMail());
    }

    @Test(expected = Exception.class)
    public void prepareChangePassword_null_login() throws Exception {
        informer.email = null;

        objectUnderTest.prepareChangeEmail(token, new RemindInformer(informer.email));
    }

    @Test(expected = Exception.class)
    public void prepareChangePassword_existing_login() throws Exception {
        informer.email = "email@wp.pl";

        objectUnderTest.prepareChangeEmail(token, new RemindInformer(informer.email));
    }

    @Test(expected = Exception.class)
    public void prepareChangePassword_wrong_login() throws Exception {
        informer.email = "alkfhjksa";

        objectUnderTest.prepareChangeEmail(token, new RemindInformer(informer.email));
    }
}
