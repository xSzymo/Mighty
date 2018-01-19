package game.mightywarriors.services.security;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenVerifier {
    @Autowired
    private UserService userService;

    public User validate(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(SystemVariablesManager.SPECIAL_JWT_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        User user = verifyToken(body);

        return user;
    }

    private User verifyToken(Claims body) {
        User user = userService.find(body.getSubject());
        if (user == null)
            return null;

        String code = body.get("code", String.class);
        if (!SystemVariablesManager.JWTTokenCollection.contains(SystemVariablesManager.DECODER_JSON.decode(code)))
            return null;

        String decode = SystemVariablesManager.DECO4DER_DB.decode(user.getTokenCode());
        if (!decode.equals(SystemVariablesManager.DECODER_JSON.decode(code)))
            return null;

        return user;
    }
}
