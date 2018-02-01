package advanced.integration.config;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.web.json.objects.security.JSONLoginObject;
import game.mightywarriors.web.rest.api.security.authorization.LoginController;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AuthorizationConfiguration extends AdvancedIntegrationTestsConfig {
    protected static String token;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginController loginController;
    @Value("${login.username}")
    private String login;
    @Value("${login.password}")
    private String password;



    @Before
    public void authorize_and_load_example_data() throws Exception {
        JSONLoginObject login = new JSONLoginObject(this.login, this.password);
        token = SystemVariablesManager.NAME_OF_SECRET_WORD_BEFORE_TOKEN + loginController.generate(login).token;
    }

    protected void authorize(String login) throws Exception {
        User user = userService.find(login);
        JSONLoginObject loginObject = new JSONLoginObject(user.getLogin(), user.getPassword());
        token = SystemVariablesManager.NAME_OF_SECRET_WORD_BEFORE_TOKEN + loginController.generate(loginObject).token;
    }
}
