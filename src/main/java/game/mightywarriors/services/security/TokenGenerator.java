package game.mightywarriors.services.security;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.generators.RandomCodeFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TokenGenerator {
    private UserService userService;
    private RandomCodeFactory randomCodeFactory;

    @Autowired
    public TokenGenerator(UserService userService, RandomCodeFactory randomCodeFactory) {
        this.userService = userService;
        this.randomCodeFactory = randomCodeFactory;
    }

    public String generateToken(User user) {
        String tokenCode = userService.find(user).getTokenCode();
        String code;

        if (tokenCode != null) {
            code = SystemVariablesManager.DECO4DER_DB.decode(tokenCode);
            code = SystemVariablesManager.ENCODER_JSON.encode(code);
        } else {
            String uniqueCode = randomCodeFactory.getUniqueToken();
            user.setTokenCode(uniqueCode);
            user.setNewToken(true);
            userService.save(user);
            code = SystemVariablesManager.ENCODER_JSON.encode(uniqueCode);
        }

        Claims claims = Jwts.claims()
                .setExpiration(new Timestamp(System.currentTimeMillis() + SystemVariablesManager.SECONDS_FOR_TOKEN_EXPIRED * 1000))
                .setSubject(user.getLogin());

        claims.put("code", code);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SystemVariablesManager.SPECIAL_JWT_SECRET_KEY)
                .compact();
    }
}
