package game.mightywarriors.web.rest.bookmarks.arena;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.arena.ArenaManager;
import game.mightywarriors.services.bookmarks.tavern.MissionFightChecker;
import game.mightywarriors.web.json.objects.bookmarks.tavern.Informer;
import game.mightywarriors.web.json.objects.bookmarks.tavern.LeftTimer;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArenaController {
    @Autowired
    private ArenaManager arenaManager;
    @Autowired
    private MissionFightChecker missionFightChecker;

    @PostMapping("secure/arena/fight")
    public FightResult fight(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody Informer userFightInformer) throws Exception {

        return arenaManager.fightUser(authorization, userFightInformer);
    }

    @PostMapping("secure/check/champion")
    public LeftTimer checkChampionBlockTime(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody Informer informer) throws Exception {

        return new LeftTimer(missionFightChecker.checkBiggestLeftTimeForChampions(authorization, informer));
    }
}
