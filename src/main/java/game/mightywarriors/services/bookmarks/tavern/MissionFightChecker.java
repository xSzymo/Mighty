package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.utilities.FightHelper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ChampionInformer;
import game.mightywarriors.web.json.objects.bookmarks.MissionFightInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MissionFightChecker {
    private static final int ONE_SECOND = 1000;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private MissionFightService missionFightService;
    @Autowired
    private FightHelper fightHelper;

    public long checkBiggestLeftTimeForChampions(String authorization, ChampionInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        return fightHelper.getBiggestBlockTimeForEnteredChampions(user, informer.championId);
    }

    public long checkLeftTimeForMissionFight(String authorization, MissionFightInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        MissionFight fight = missionFightService.find(informer.missionFightId);

        throwExceptionIf_UserHaveNotSpecificMissionFight(user, fight);

        return (fight.getBlockUntil().getTime() - (new Timestamp(System.currentTimeMillis()).getTime()) / ONE_SECOND);
    }

    private void throwExceptionIf_UserHaveNotSpecificMissionFight(User user, MissionFight fight) throws Exception {
        if (user.getChampions().stream().noneMatch(x -> fight.getChampions().iterator().next().getId().equals(x.getId())))
            throw new Exception("Something went wrong");
    }

}
