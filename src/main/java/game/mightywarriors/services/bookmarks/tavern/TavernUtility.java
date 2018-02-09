package game.mightywarriors.services.bookmarks.tavern;

import game.mightywarriors.data.repositories.UserRepository;
import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.MissionFightService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.background.tasks.MissionAssigner;
import game.mightywarriors.services.bookmarks.utilities.FightHelper;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Service
public class TavernUtility {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MissionFightService missionFightService;
    @Autowired
    private ChampionService championService;
    @Autowired
    private UserService userService;
    @Autowired
    private MissionAssigner missionAssigner;
    @Autowired
    private FightHelper fightHelper;

    private static final int ONE_SECOND = 1000;

    public void prepareNewMissionFight(Set<Champion> champions, Mission mission) {
        MissionFight missionFight = new MissionFight();

        Timestamp blockDate = new Timestamp(System.currentTimeMillis() + (mission.getDuration() * ONE_SECOND));
        champions.forEach(x -> x.setBlockUntil(blockDate));
        missionFight.setBlockUntil(blockDate);
        missionFight.setChampions(champions);
        missionFight.setMission(mission);

        championService.save(champions);
        missionFightService.save(missionFight);

        missionAssigner.assignNewMissionForUsers(userRepository.findByChampionsIdIn(fightHelper.getSetOfChampionsIds(champions)).getId());
    }

    public void getThingsDoneAfterFight(User user, MissionFight missionFight, Set<Champion> champions, FightResult fight, boolean wonFight) {
        if (wonFight) {
            long missionExperience = missionFight.getMission().getExperience();
            BigDecimal missionGold = missionFight.getMission().getGold();

            champions = fightHelper.getChampions(user, fightHelper.getChampionsId(champions));

            long exp = missionExperience / champions.size();
            champions.forEach(x -> x.addExperience(exp));
            user.addGold(missionGold);

            fight.setExperience(missionExperience);
            fight.setGold(missionGold);
        }

        user.setMissionPoints(user.getMissionPoints() - 1);
        champions.forEach(x -> x.setBlockUntil(null));

        missionFightService.delete(missionFight);
        userService.save(user);
    }
}
