package integration.services.bookmarks.registration;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.registration.AccountEnabler;
import game.mightywarriors.services.registration.RegistrationManager;
import game.mightywarriors.web.json.objects.bookmarks.CodeInformer;
import game.mightywarriors.web.json.objects.bookmarks.RegistrationInformer;
import integration.config.IntegrationTestsConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AccountEnablerTest extends IntegrationTestsConfig {
    @Autowired
    private AccountEnabler objectUnderTest;
    @Autowired
    private RegistrationManager registrationManager;
    @Autowired
    private UserService userService;

    private String login = "test";
    private String password = "Testt123";
    private String email = SystemVariablesManager.EMAIL_TEST_USERNAME;

    @Test
    public void enableUser() throws Exception {
        RegistrationInformer informer = setUp();
        User user = userService.find(informer.login);

        objectUnderTest.enableAccount(new CodeInformer(user.getCodeToEnableAccount()));

        assertEquals(null, userService.find(informer.login).getCodeToEnableAccount());
        assertEquals(true, userService.find(informer.login).isAccountEnabled());
    }

    @Test(expected = Exception.class)
    public void enableUser_with_wrong_code() throws Exception {
        RegistrationInformer informer = new RegistrationInformer();

        objectUnderTest.enableAccount(new CodeInformer(""));
    }

    private RegistrationInformer setUp() throws Exception {
        RegistrationInformer informer = new RegistrationInformer();
        informer.login = login;
        informer.password = password;
        informer.email = email;

        registrationManager.registerUser(informer);

        assertEquals(login, userService.find(login).getLogin());
        assertEquals(password, userService.find(login).getPassword());
        assertEquals(email, userService.find(login).geteMail());
        assertNotEquals(null, userService.find(login).getCodeToEnableAccount());
        assertEquals(false, userService.find(login).isAccountEnabled());
        assertEquals(true, userService.find(login).isAccountNonLocked());

        return informer;
    }
}

