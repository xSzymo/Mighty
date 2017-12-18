package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Service
public class SenderManager {
    @Autowired
    private MissionFightService missionFightService;
    @Autowired
    private ChampionService championService;
    @Autowired
    private UserService userService;

    protected static final int ONE_SECOND = 1000;

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

    public void getThingsDoneAtTheEndOfFight(User user, MissionFight missionFight, List<Champion> champions, boolean wonFight) {
        if (wonFight) {
            long exp = missionFight.getMission().getExperience() / champions.size();
            champions.forEach(x -> x.addExperience(exp));
            user.addGold(missionFight.getMission().getGold());
        }

        champions.forEach(x -> x.setBlockTime(null));
        missionFightService.delete(missionFight);
        championService.save(new LinkedList<>(champions));
        userService.save(user);
    }
}
