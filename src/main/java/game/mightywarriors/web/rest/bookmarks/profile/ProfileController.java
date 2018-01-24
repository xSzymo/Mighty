package game.mightywarriors.web.rest.bookmarks.profile;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.other.enums.StatisticType;
import game.mightywarriors.services.bookmarks.profile.ChampionPointsManager;
import game.mightywarriors.web.json.objects.bookmarks.tavern.PointsInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    @Autowired
    private ChampionPointsManager championPointsManager;

    @PostMapping("secure/profile/statistic")
    public void fight(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PointsInformer informer) throws Exception {
        for (StatisticType type : StatisticType.values()) {
            if (type.getType().equals(informer.statisticName)) {
                championPointsManager.addPoints(authorization, type, informer.championId);
                return;
            }
        }
    }
}
