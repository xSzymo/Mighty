package game.mightywarriors.web.rest.Authorization;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.jsonObjects.JSONLoginObject;
import game.mightywarriors.other.jsonObjects.JSONTokenObject;
import game.mightywarriors.services.GenerateTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TokenController {
    @Autowired
    private UserService userService;
    @Autowired
    private GenerateTokenService generateTokenService;

    @PostMapping("token")
    public JSONTokenObject generate(@RequestBody JSONLoginObject loginData) throws Exception {
        User myUser = userService.findByLogin(loginData.login);
        if (myUser == null)
            throw new Exception("Wrong login or password");

        if (!myUser.getPassword().equals(loginData.password))
            throw new Exception("Wrong login or password");

        return new JSONTokenObject(generateTokenService.generateToken(myUser));
    }

    @PostMapping("secure/refresh")
    public JSONTokenObject refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String token = httpServletRequest.getHeader(SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN);
        Claims body = Jwts.parser()
                .setSigningKey(SystemVariablesManager.SPECIAL_JWT_SECRET_KEY)
                .parseClaimsJws(token.substring(7))
                .getBody();

        return new JSONTokenObject(generateTokenService.generateToken(userService.findByLogin(body.getSubject())));
    }
}
