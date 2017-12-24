package game.mightywarriors.web.rest.bookmarks.tavern;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.services.bookmarks.tavern.TavernManager;
import game.mightywarriors.services.bookmarks.utilities.arena.and.tavern.MissionFightChecker;
import game.mightywarriors.web.json.objects.bookmarks.tavern.MissionFightInformer;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TavernController {
    @Autowired
    private TavernManager tavernManager;
    @Autowired
    private MissionFightChecker missionFightChecker;

    @PostMapping("secure/tavern/fight")
    public FightResult fight(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MissionFightInformer missionFightInformer) throws Exception {

        return tavernManager.performFight(authorization, missionFightInformer);
    }

    @PostMapping("secure/tavern/send")
    public MissionFight sendChampionOnMission(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MissionFightInformer missionFightInformer) throws Exception {

        return tavernManager.sendChampionOnMission(authorization, missionFightInformer);
    }

    @PostMapping("secure/tavern/check/mission")
    public long checkMissionFightBlockTime(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MissionFightInformer missionFightInformer) throws Exception {

        return missionFightChecker.checkLeftTimeForMissionFight(authorization, missionFightInformer);
    }
}
