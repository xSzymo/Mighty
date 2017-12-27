package game.mightywarriors.web.rest.security.principal;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.security.PrincipalJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PrincipalController {
    @Autowired
    private UsersRetriever retriever;

    @GetMapping("secure/getPrincipal")
    public Object getPrincipal(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        return new PrincipalJSON(retriever.retrieveUserDetails(authorization).getAuthorities());
    }

    @GetMapping("secure/getCurrentUser")
    public User getCurrentUser(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        return retriever.retrieveUser(authorization);
    }
}
