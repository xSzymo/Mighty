package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.utilities.FightHelper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ChampionInformer;
import game.mightywarriors.web.json.objects.bookmarks.MissionFightInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class MissionFightChecker {
    private UsersRetriever usersRetriever;
    private MissionFightService missionFightService;
    private FightHelper fightHelper;

    private static final int ONE_SECOND = 1000;

    @Autowired
    public MissionFightChecker(UsersRetriever usersRetriever, MissionFightService missionFightService, FightHelper fightHelper) {
        this.usersRetriever = usersRetriever;
        this.missionFightService = missionFightService;
        this.fightHelper = fightHelper;
    }

    public long checkBiggestLeftTimeForChampions(String authorization, ChampionInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        return fightHelper.getBiggestBlockTimeForEnteredChampions(user, informer.championId);
    }

    public long checkLeftTimeForMissionFight(String authorization, MissionFightInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        MissionFight fight = missionFightService.find(informer.missionFightId);

        throwExceptionIf_MissionFightIsNotPresent(fight, informer.missionFightId);
        throwExceptionIf_UserHaveNotSpecificMissionFight(user, fight);

        return (fight.getBlockUntil().getTime() - (new Timestamp(System.currentTimeMillis()).getTime())) / ONE_SECOND;
    }

    private void throwExceptionIf_UserHaveNotSpecificMissionFight(User user, MissionFight fight) throws Exception {
        Optional<Champion> foundChampion = fight.getChampions().stream().findFirst();
        if(!foundChampion.isPresent())
            throw new Exception("Something went wrong");

        if (user.getChampions().stream().noneMatch(x -> foundChampion.get().getId().equals(x.getId())))
            throw new Exception("Something went wrong");
    }

    private void throwExceptionIf_MissionFightIsNotPresent(MissionFight missionFight, long id) throws Exception {
        if(missionFight == null)
            throw new Exception("Mission fight not found id : " + id);
    }
}
