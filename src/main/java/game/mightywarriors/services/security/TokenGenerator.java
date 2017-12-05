package game.mightywarriors.services.security;

import game.mightywarriors.configuration.system.SystemVariablesManager;
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
    @Autowired
    private UserService userService;
    @Autowired
    private RandomCodeFactory randomCodeFactory;

    public String generateToken(User user) {
        String tokenCode = user.getTokenCode();
        if (tokenCode != null)
            if (SystemVariablesManager.JWTTokenCollection.contains(SystemVariablesManager.DECO4DER_DB.decode(tokenCode)))
                SystemVariablesManager.JWTTokenCollection.remove(SystemVariablesManager.DECO4DER_DB.decode(tokenCode));

        String uniqueCode = randomCodeFactory.getUniqueCode();
        user.setTokenCode(uniqueCode);
        userService.save(user);

        String code = SystemVariablesManager.ENCODER_JSON.encode(uniqueCode);

        Claims claims = Jwts.claims().setExpiration(new Timestamp(System.currentTimeMillis() + SystemVariablesManager.SECONDS_FOR_TOKEN_EXPIRED * 1000))
                .setSubject(user.getLogin());
        claims.put("code", code);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SystemVariablesManager.SPECIAL_JWT_SECRET_KEY)
                .compact();
    }
}
