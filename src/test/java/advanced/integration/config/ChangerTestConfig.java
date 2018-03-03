package advanced.integration.config;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.web.json.objects.bookmarks.OptionInformer;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ChangerTestConfig extends AuthorizationConfiguration {
    @Autowired
    protected UserService userService;

    protected User user;
    protected OptionInformer informer;
    protected String login = "testing" + System.currentTimeMillis();
    protected String password = "Admino12345";
    protected String newLogin = "testing1" + System.currentTimeMillis();
    protected String newPassword = "Admino123456";
    protected String newEmail = "testtest630@wp.pl";

    @Before
    public void setUp() throws Exception {
        user = new User(login, password, SystemVariablesManager.EMAIL_TEST_USERNAME);
        user.setAccountEnabled(true);
        userService.save(user);

        authorize(user.getLogin());

        informer = new OptionInformer();
        informer.login = newLogin;
        informer.password = newPassword;
        informer.email = newEmail;
    }

    @After
    public void cleanUp() {
        userService.delete(user);
    }
}
