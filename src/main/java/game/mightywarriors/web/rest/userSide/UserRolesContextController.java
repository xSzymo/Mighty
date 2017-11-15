package game.mightywarriors.web.rest.userSide;

import game.mightywarriors.configuration.jwt.model.JwtAuthenticationToken;
import game.mightywarriors.configuration.jwt.security.JwtAuthenticationProvider;
import game.mightywarriors.configuration.jwt.security.JwtAuthenticationTokenFilter;
import game.mightywarriors.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRolesContextController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @GetMapping("secure/getPrincipal")
    public Object getPrincipal(@RequestHeader(value = "Authorization") String Authorization) throws Exception {
        if (Authorization == null || Authorization.equals(""))
            throw new Exception("Wrong token");

        UserDetails userDetails = jwtAuthenticationProvider.retrieveUser("NONE_PROVIDED", new JwtAuthenticationToken(Authorization));
        return userDetails.getAuthorities();
    }

    @GetMapping("secure/getCurrentUser")
    public Object getCurrentUser(@RequestHeader(value = "Authorization") String Authorization) throws Exception {
        if (Authorization == null || Authorization.equals(""))
            throw new Exception("Wrong token");

        UserDetails userDetails = jwtAuthenticationProvider.retrieveUser("NONE_PROVIDED", new JwtAuthenticationToken(Authorization));
        return userRepository.findByLogin(userDetails.getName());
    }
}
