package game.mightywarriors.web.rest.api.bookmarks.dungeon;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.DungeonFightService;
import game.mightywarriors.data.tables.DungeonFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.dungeon.DungeonManager;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DungeonManagerController {
    @Autowired
    private DungeonManager dungeonManager;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private DungeonFightService dungeonFightService;

    @PostMapping("secure/dungeon/send")
    public void sendChampionOnMission(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {

        dungeonManager.sendChampionsToDungeon(authorization);
    }

    @PostMapping("secure/dungeon/fight")
    public FightResult fight(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {

        return dungeonManager.performFightDungeonFight(authorization);
    }

    @PostMapping("secure/dungeon/check")
    public DungeonFight checkMissionFightBlockTime(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        return dungeonFightService.findByUserId(user.getId());
    }
}
