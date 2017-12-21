package game.mightywarriors.web.rest.bookmarks.tavern;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.services.bookmarks.tavern.MissionChampionSender;
import game.mightywarriors.services.bookmarks.tavern.MissionFightChecker;
import game.mightywarriors.web.json.objects.fights.FightResult;
import game.mightywarriors.web.json.objects.bookmarks.tavern.MissionFightInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TavernController {
    @Autowired
    private MissionChampionSender missionChampionSender;
    @Autowired
    private MissionFightChecker missionFightChecker;

    @PostMapping("secure/tavern/fight")
    public FightResult fight(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MissionFightInformer missionFightInformer) throws Exception {

        return missionChampionSender.performFight(authorization, missionFightInformer);
    }

    @PostMapping("secure/tavern/send")
    public MissionFight sendChampionOnMission(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MissionFightInformer missionFightInformer) throws Exception {

        return missionChampionSender.sendChampionOnMission(authorization, missionFightInformer);
    }

    @PostMapping("secure/tavern/check/champion")
    public long checkChampionBlockTime(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MissionFightInformer missionFightInformer) throws Exception {

        return missionFightChecker.checkBiggestLeftTimeForChampions(authorization, missionFightInformer);
    }

    @PostMapping("secure/tavern/check/mission")
    public long checkMissionFightBlockTime(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MissionFightInformer missionFightInformer) throws Exception {

        return missionFightChecker.checkLeftTimeForMissionFight(authorization, missionFightInformer);
    }
}
