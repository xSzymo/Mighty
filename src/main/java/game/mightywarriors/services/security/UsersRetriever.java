package game.mightywarriors.services.security;

import game.mightywarriors.configuration.jwt.model.JwtAuthenticationToken;
import game.mightywarriors.configuration.jwt.security.JwtAuthenticationProvider;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsersRetriever {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    public User retrieveUser(String authorization) throws Exception {
        if (authorization == null || authorization.equals(""))
            throw new Exception("Wrong token");

        UserDetails userDetails = jwtAuthenticationProvider.retrieveUser("NONE_PROVIDED", new JwtAuthenticationToken(authorization));
        return userService.find(userDetails.getName());
    }

    public UserDetails retrieveUserDetails(String authorization) throws Exception {
        if (authorization == null || authorization.equals(""))
            throw new Exception("Wrong token");

        return jwtAuthenticationProvider.retrieveUser("NONE_PROVIDED", new JwtAuthenticationToken(authorization));
    }
}
