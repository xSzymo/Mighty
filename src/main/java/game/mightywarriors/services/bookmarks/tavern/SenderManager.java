package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.background.tasks.MissionAssigner;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Service
public class SenderManager {
    protected static final int ONE_SECOND = 1000;

    @Autowired
    private MissionFightService missionFightService;
    @Autowired
    private ChampionService championService;
    @Autowired
    private UserService userService;
    @Autowired
    private MissionAssigner missionAssigner;

    public MissionFight prepareNewMissionFight(LinkedList<Champion> champions, Mission mission) {
        MissionFight missionFight = new MissionFight();

        Timestamp blockDate = new Timestamp(System.currentTimeMillis() + (mission.getTimeDuration() * ONE_SECOND));
        champions.forEach(x -> x.setBlockTime(blockDate));
        missionFight.setBlockTime(blockDate);
        missionFight.setChampion(champions);
        missionFight.setMission(mission);

        championService.save(champions);
        missionFightService.save(missionFight);

        return missionFight;
    }

    public void getThingsDoneAfterFight(User user, MissionFight missionFight, List<Champion> champions, FightResult fight, boolean wonFight) {
        if (wonFight) {
            long missionExperience = missionFight.getMission().getExperience();
            BigDecimal missionGold = missionFight.getMission().getGold();

            long exp = missionExperience / champions.size();
            champions.forEach(x -> x.addExperience(exp));
            user.addGold(missionGold);

            fight.setExperience(missionExperience);
            fight.setGold(missionGold);
        }

        user.setMissionPoints(user.getMissionPoints() - 1);
        champions.forEach(x -> x.setBlockTime(null));

        missionFightService.delete(missionFight);
        championService.save(new LinkedList<>(champions));
        userService.save(user);

        missionAssigner.assignNewMissionForUsers(user.getId());
    }
}
