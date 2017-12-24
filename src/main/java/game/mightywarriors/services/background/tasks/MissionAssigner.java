package game.mightywarriors.services.background.tasks;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.services.MissionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MissionAssigner {
    private static Random rand;

    @Autowired
    private UserService userService;
    @Autowired
    private MissionService missionService;
    private Map map;

    public MissionAssigner() {
        rand = new Random();
    }

    public void assignNewMissionForUsers(long id) {
        LinkedList<User> users = new LinkedList();
        users.add(userService.findOne(id));

        assign(users);
    }

    public void assignNewMissionForUsers() {
        assign(userService.findAll());
    }

    @Transactional
    private void assign(LinkedList<User> users) {
        LinkedList<Mission> missions = missionService.findAll();
        map = new HashMap();

        if (missions.size() == 0)
            throw new RuntimeException("restart system");

        draw(users, missions, false);
        draw(users, missions, true);

        userService.save(users);
    }

    private LinkedList<User> draw(LinkedList<User> users, LinkedList<Mission> missions, boolean clear) {
        users.forEach(user -> {
            if (user.getChampions().size() == 0)
                throw new RuntimeException("restart system");

            List<Mission> oldMissions = new LinkedList<>(user.getMissions());
            if (oldMissions.size() == 0)
                oldMissions = new LinkedList<>();

            if (clear)
                user.getMissions().clear();

            List<Mission> missionForSpecifiedLevel = new LinkedList<>(getAllMissionsForSpecificLevel(missions, user.getUserChampiongHighestLevel()));
            while (user.getMissions().size() < 3 && missionForSpecifiedLevel.size() > 0) {
                Mission mission = null;
                try {
                    mission = missionForSpecifiedLevel.get(rand.nextInt(missionForSpecifiedLevel.size() > 0 ? missionForSpecifiedLevel.size() : 1));

                    if (mission.getMonster().getLevel() > user.getUserChampiongHighestLevel())
                        continue;

                    if (oldMissions.contains(mission))
                        continue;
                    user.addMission(mission);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    missionForSpecifiedLevel.remove(mission);
                }
            }
        });

        return users;
    }

    private List<Mission> getAllMissionsForSpecificLevel(LinkedList<Mission> missions, long level) {
        if (map.get(level) == null)
            map.put(level, missions.stream().filter(x -> x.getMonster().getLevel() <= level && x.getMonster().getLevel() >= level - SystemVariablesManager.MISSION_MONSTER_LEVEL_UNDER_CHAMPION_LEVEL).collect(Collectors.toList()));

        return (List<Mission>) map.get(level);
    }
}
