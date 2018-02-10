package game.mightywarriors.web.rest.api.data.user;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.DungeonFightService;
import game.mightywarriors.data.tables.DungeonFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DungeonFightsController {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private DungeonFightService dungeonFightService;

    @PostMapping("secure/dungeonFights")
    public DungeonFight checkMissionFightBlockTime(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        return dungeonFightService.findByUserId(user.getId());
    }
}
