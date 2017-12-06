package integration.game.mightywarriors.services.security;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.TokenGenerator;
import integration.game.mightywarriors.config.IntegrationTestsConfig;
import io.jsonwebtoken.Jwts;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TokenGeneratorTest extends IntegrationTestsConfig {
    @Autowired
    private TokenGenerator tokenService;
    @Autowired
    private UserService userService;

    @After
    public void cleanUp() {
        SystemVariablesManager.JWTTokenCollection.clear();
    }

    @Test
    public void generateToken() {
        User user = new User("example", "", "");
        userService.save(user);

        String token = tokenService.generateToken(user);

        String myToken = SystemVariablesManager.JWTTokenCollection.get(0);
        assertEquals(myToken, SystemVariablesManager.DECO4DER_DB.decode(user.getTokenCode()));
        assertEquals(myToken, SystemVariablesManager.DECO4DER_DB.decode(userService.findOne(user).getTokenCode()));
        assertEquals(myToken, SystemVariablesManager.DECODER_JSON.decode(Jwts.parser()
                .setSigningKey(SystemVariablesManager.SPECIAL_JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().get("code", String.class)));

        token = tokenService.generateToken(user);

        assertFalse(SystemVariablesManager.JWTTokenCollection.contains(myToken));
        assertNotEquals(myToken, token);

        myToken = SystemVariablesManager.JWTTokenCollection.get(0);
        assertEquals(myToken, SystemVariablesManager.DECO4DER_DB.decode(user.getTokenCode()));
        assertEquals(myToken, SystemVariablesManager.DECO4DER_DB.decode(userService.findOne(user).getTokenCode()));
        assertEquals(myToken, SystemVariablesManager.DECODER_JSON.decode(Jwts.parser()
                .setSigningKey(SystemVariablesManager.SPECIAL_JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().get("code", String.class)));
    }
}
