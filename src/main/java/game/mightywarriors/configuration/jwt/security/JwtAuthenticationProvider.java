package game.mightywarriors.configuration.jwt.security;

import game.mightywarriors.configuration.jwt.model.JwtAuthenticationToken;
import game.mightywarriors.configuration.jwt.model.JwtUserDetails;
import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private UserService userService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    public UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = jwtAuthenticationToken.getToken();
        if (!token.contains("Bearer "))
            throw new RuntimeException("JWT Token is incorrect");

        token = token.replace("Bearer ", "");

        User user = validate(token);

        if (user == null) {
            throw new RuntimeException("JWT Token is incorrect");
        }

        List<GrantedAuthority> grantedAuthorities = new LinkedList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole().getRole()));
        return new JwtUserDetails(user, grantedAuthorities);
    }

    private User validate(String token) {
        User user = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SystemVariablesManager.SPECIAL_JWT_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            user = verifyToken(token, body);
        } catch (Exception e) {
            System.out.println(e);
        }

        return user;
    }

    private User verifyToken(String token, Claims body) {
        User user = userService.findByLogin(body.getSubject());
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

    @Override
    public boolean supports(Class<?> aClass) {
        return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
