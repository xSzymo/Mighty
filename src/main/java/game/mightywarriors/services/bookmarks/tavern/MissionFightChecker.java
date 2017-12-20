package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.tavern.MissionFightInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MissionFightChecker {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private MissionFightService missionFightService;
    @Autowired
    private Helper helper;

    public long checkBiggestLeftTimeForChampions(String authorization, MissionFightInformer missionFightInformer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        return helper.getBiggestBlockTimeForEnteredChampions(user, missionFightInformer.championId);
    }

    public long checkLeftTimeForMissionFight(String authorization, MissionFightInformer missionFightInformer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        MissionFight one = missionFightService.findOne(missionFightInformer.id);

        if (user.getChampions().stream().noneMatch(x -> one.getChampion().get(0).getId().equals(x.getId())))
            throw new Exception("Something went wrong");

        return (one.getBlockDate().getTime() - (new Timestamp(System.currentTimeMillis()).getTime()) / Helper.ONE_SECOND);
    }
}
