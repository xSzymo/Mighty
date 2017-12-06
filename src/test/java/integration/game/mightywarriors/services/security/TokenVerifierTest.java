package integration.game.mightywarriors.services.security;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.TokenGenerator;
import game.mightywarriors.services.security.TokenVerifier;
import integration.game.mightywarriors.config.IntegrationTestsConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static junit.framework.TestCase.assertEquals;

public class TokenVerifierTest extends IntegrationTestsConfig {
    @Autowired
    private TokenVerifier objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenGenerator tokenGenerator;

    private User user;

    @Before
    public void setUp() {
        user = new User("user", "", "");
        userService.save(user);
    }

    @Test
    @Transactional
    public void validate() {
        String token = tokenGenerator.generateToken(user);
        User myUser = objectUnderTest.validate(token);

        assertEquals(user, myUser);
    }
}
