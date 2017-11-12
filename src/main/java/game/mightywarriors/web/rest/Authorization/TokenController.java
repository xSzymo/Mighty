package game.mightywarriors.web.rest.Authorization;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
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

    @PostMapping
    public Token generate(@RequestBody User user) throws Exception {
        User myUser = userService.findByLogin(user.getLogin());
        if (myUser == null)
            throw new Exception("Wrong login or password");

        if (!myUser.getPassword().equals(user.getPassword()))
            throw new Exception("Wrong login or password");

        return new Token(generateToken(myUser));
    }

    public String generateToken(User user) {
        Claims claims = Jwts.claims()
                .setSubject(user.getLogin());
        claims.put("userId", String.valueOf(user.getId()));
        claims.put("login", String.valueOf(user.getLogin()));
        claims.put("role", user.getUserRole().getRole());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "K00LINN3R")
                .compact();
    }

}
