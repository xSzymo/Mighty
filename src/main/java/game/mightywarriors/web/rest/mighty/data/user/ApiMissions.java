package game.mightywarriors.web.rest.mighty.data.user;


import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.MissionService;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ApiMissions {
    @Autowired
    private UsersRetriever retriever;

    @GetMapping("secure/missions")
    public Set<Mission> getUsersMission(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);
        return user.getMissions();
    }
}
