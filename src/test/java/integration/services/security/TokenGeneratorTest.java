package integration.services.security;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.TokenGenerator;
import integration.config.IntegrationTestsConfig;
import io.jsonwebtoken.Jwts;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TokenGeneratorTest extends IntegrationTestsConfig {
    @Autowired
    private TokenGenerator objectUnderTest;
    @Autowired
    private UserService userService;
    private User user;

    @Before
    public void setUp() {
        user = new User("example", "", "");
        userService.save(user);
    }

    @After
    public void cleanUp() {
        SystemVariablesManager.JWTTokenCollection.clear();
    }

    @Test
    public void generateToken() {
        String token = objectUnderTest.generateToken(user);

        String myToken = checker(user, token);

        token = objectUnderTest.generateToken(user);

        assertFalse(SystemVariablesManager.JWTTokenCollection.contains(myToken));
        assertNotEquals(myToken, token);
        checker(user, token);
    }

    private String checker(User user, String token) {
        String myToken = SystemVariablesManager.JWTTokenCollection.get(0);
        String parsedToken = SystemVariablesManager.DECODER_JSON.decode(Jwts.parser()
                .setSigningKey(SystemVariablesManager.SPECIAL_JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().get("code", String.class));

        assertEquals(myToken, SystemVariablesManager.DECO4DER_DB.decode(user.getTokenCode()));
        assertEquals(myToken, SystemVariablesManager.DECO4DER_DB.decode(userService.findOne(user).getTokenCode()));
        assertNotEquals(token, user.getTokenCode());
        assertEquals(myToken, parsedToken);
        assertNotEquals(token, parsedToken);

        return myToken;
    }
}
