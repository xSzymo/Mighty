package game.mightywarriors.web.rest.Authorization;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.generators.RandomCodeFactory;
import game.mightywarriors.other.jsonObjects.JSONLoginObject;
import game.mightywarriors.other.jsonObjects.JSONTokenObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    private UserService userService;
    @Autowired
    private RandomCodeFactory randomCodeFactory;

    @PostMapping
    public JSONTokenObject generate(@RequestBody JSONLoginObject loginData) throws Exception {
        User myUser = userService.findByLogin(loginData.login);
        if (myUser == null)
            throw new Exception("Wrong login or password");

        if (!myUser.getPassword().equals(loginData.password))
            throw new Exception("Wrong login or password");

        return new JSONTokenObject(generateToken(myUser));
    }

    private String generateToken(User user) {
        String uniqueCode = randomCodeFactory.getUniqueCode();
        user.setTokenCode(uniqueCode);
        userService.save(user);

        String code = SystemVariablesManager.ENCODER_JSON.encode(uniqueCode);

        Claims claims = Jwts.claims()
                .setSubject(user.getLogin());
        claims.put("userId", String.valueOf(user.getId()));
        claims.put("code", code);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SystemVariablesManager.SPECIAL_JWT_SECRET_KEY)
                .compact();
    }
}
