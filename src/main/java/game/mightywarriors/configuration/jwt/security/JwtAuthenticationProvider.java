package game.mightywarriors.configuration.jwt.security;

import game.mightywarriors.configuration.jwt.model.JwtAuthenticationToken;
import game.mightywarriors.configuration.jwt.model.JwtUserDetails;
import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.TokenVerifier;
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
    private TokenVerifier tokenVerifier;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    public UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = jwtAuthenticationToken.getToken();
        if (!token.contains(SystemVariablesManager.NAME_OF_SPECIAL_SHIT))
            throw new RuntimeException("JWT Token is incorrect");

        token = token.replace(SystemVariablesManager.NAME_OF_SPECIAL_SHIT, "");

        User user;
        try {
            user = tokenVerifier.validate(token);
        } catch (Exception e) {
            throw new RuntimeException("JWT Token expired");
        }

        if (user == null) {
            throw new RuntimeException("JWT Token is incorrect");
        }

        List<GrantedAuthority> grantedAuthorities = new LinkedList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole().getRole()));
        return new JwtUserDetails(user, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
