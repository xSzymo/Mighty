package game.mightywarriors.services.security;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UsersRetriever {
    @Autowired
    private UserService userService;

    public User retrieveUser(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN);
        Claims body = Jwts.parser()
                .setSigningKey(SystemVariablesManager.SPECIAL_JWT_SECRET_KEY)
                .parseClaimsJws(token.substring(SystemVariablesManager.NAME_OF_SPECIAL_SHIT.length()))
                .getBody();

        return userService.findByLogin(body.getSubject());
    }
}
