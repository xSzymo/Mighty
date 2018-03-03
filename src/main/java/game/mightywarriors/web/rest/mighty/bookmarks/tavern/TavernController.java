package game.mightywarriors.web.rest.mighty.bookmarks.tavern;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.tavern.ChampionTavern;
import game.mightywarriors.services.bookmarks.tavern.MissionFightChecker;
import game.mightywarriors.services.bookmarks.tavern.TavernManager;
import game.mightywarriors.web.json.objects.bookmarks.ChampionBuyInformer;
import game.mightywarriors.web.json.objects.bookmarks.MissionFightInformer;
import game.mightywarriors.web.json.objects.bookmarks.TavernInformer;
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
    private ChampionTavern buyer;
    @Autowired
    private MissionFightChecker missionFightChecker;

    @PostMapping("secure/tavern/send")
    public void sendChampionOnMission(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody TavernInformer informer) throws Exception {

        tavernManager.sendChampionOnMission(authorization, informer);
    }

    @PostMapping("secure/tavern/fight")
    public FightResult fight(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MissionFightInformer informer) throws Exception {

        return tavernManager.performFight(authorization, informer);
    }

    @PostMapping("secure/tavern/check/mission")
    public long checkMissionFightBlockTime(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MissionFightInformer informer) throws Exception {

        return missionFightChecker.checkLeftTimeForMissionFight(authorization, informer);
    }

    @PostMapping("secure/tavern/champion/buy")
    public void buyChampion(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {

        buyer.buyChampion(authorization);
    }

    @PostMapping("secure/tavern/champion/buy/information")
    public ChampionBuyInformer getInformationForBuyChampion(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {

        return buyer.getInformationForBuyChampion(authorization);
    }
}
