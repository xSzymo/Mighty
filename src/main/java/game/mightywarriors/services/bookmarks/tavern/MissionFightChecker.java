package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.utilities.FightHelper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.Informer;
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

    public long checkBiggestLeftTimeForChampions(String authorization, Informer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        return fightHelper.getBiggestBlockTimeForEnteredChampions(user, informer.championId);
    }

    public long checkLeftTimeForMissionFight(String authorization, Informer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        MissionFight fight = missionFightService.find(informer.missionFightId);

        if (user.getChampions().stream().noneMatch(x -> fight.getChampions().iterator().next().getId().equals(x.getId())))
            throw new Exception("Something went wrong");

        return (fight.getBlockUntil().getTime() - (new Timestamp(System.currentTimeMillis()).getTime()) / ONE_SECOND);
    }
}
