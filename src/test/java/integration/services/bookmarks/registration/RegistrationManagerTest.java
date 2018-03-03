package integration.services.bookmarks.registration;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.services.registration.RegistrationManager;
import game.mightywarriors.web.json.objects.bookmarks.RegistrationInformer;
import integration.config.IntegrationTestsConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RegistrationManagerTest extends IntegrationTestsConfig {
    @Autowired
    private RegistrationManager objectUnderTest;
    @Autowired
    private UserService userService;

    private String login = "test";
    private String password = "Testt123";
    private String email = SystemVariablesManager.EMAIL_TEST_USERNAME;

    @Test
    public void registerUser() throws Exception {
        RegistrationInformer informer = new RegistrationInformer();
        informer.login = login;
        informer.password = password;
        informer.email = email;

        objectUnderTest.registerUser(informer);

        assertEquals(login, userService.find(login).getLogin());
        assertEquals(password, userService.find(login).getPassword());
        assertEquals(email, userService.find(login).geteMail());
        assertNotEquals(null, userService.find(login).getCodeToEnableAccount());
        assertEquals(false, userService.find(login).isAccountEnabled());
        assertEquals(false, userService.find(login).isAccountNonLocked());
    }

    @Test(expected = Exception.class)
    public void registerUser_taken_login() throws Exception {
        RegistrationInformer informer = new RegistrationInformer();
        informer.login = login;
        informer.password = password;
        informer.email = email;

        objectUnderTest.registerUser(informer);
        informer.email = "testtest630@wp.pl";
        objectUnderTest.registerUser(informer);
    }

    @Test(expected = Exception.class)
    public void registerUser_taken_email() throws Exception {
        RegistrationInformer informer = new RegistrationInformer();
        informer.login = login;
        informer.password = password;
        informer.email = email;
        objectUnderTest.registerUser(informer);
        informer.login = "asd";
        objectUnderTest.registerUser(informer);
    }
}
