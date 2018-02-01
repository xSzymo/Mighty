package game.mightywarriors.web.rest.api.security.authorization;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.services.security.TokenGenerator;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.security.JSONLoginObject;
import game.mightywarriors.web.json.objects.security.JSONTokenObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private UsersRetriever usersRetriever;

    @PostMapping("token")
    public JSONTokenObject generate(@RequestBody JSONLoginObject loginData) throws Exception {
        User myUser = userService.find(loginData.login);
        if (myUser == null)
            throw new NoAccessException("Wrong login or password");

        if (!myUser.getPassword().equals(loginData.password))
            throw new NoAccessException("Wrong login or password");

        return new JSONTokenObject(tokenGenerator.generateToken(myUser), myUser.getId());
    }

    @PostMapping("secure/refresh")
    public JSONTokenObject refresh(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        return new JSONTokenObject(tokenGenerator.generateToken(user), user.getId());
    }

    @PostMapping("secure/token/delete")
    public void deletToken(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        SystemVariablesManager.JWTTokenCollection.remove(SystemVariablesManager.DECO4DER_DB.decode(user.getTokenCode()));

        user.setTokenCode(null);
        user.setNewToken(false);
        userService.save(user);
    }
}
