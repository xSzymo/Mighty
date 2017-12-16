package game.mightywarriors.web.rest.userSide;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRolesContextController {
    @Autowired
    private UsersRetriever retriever;

    @GetMapping("secure/getPrincipal")
    public Object getPrincipal(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        return retriever.retrieveUserDetails(authorization).getAuthorities();
    }

    @GetMapping("secure/getCurrentUser")
    public Object getCurrentUser(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        return retriever.retrieveUser(authorization);
    }
}
