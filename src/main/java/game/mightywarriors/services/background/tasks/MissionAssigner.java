package game.mightywarriors.services.background.tasks;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
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

    public MissionAssigner() {
        rand = new Random();
    }

    @Transactional
    public void assignNewMissionForUsers(long id) {
        HashSet<User> users = new HashSet<>();
        users.add(userService.find(id));

        assign(users);
    }

    @Transactional
    public synchronized void assignNewMissionForUsers() {
        assign(userService.findAll());
    }

    private void assign(Set<User> users) {
        HashSet<Mission> missions = missionService.findAll();
        missions = missions.stream().filter(x -> x.getMonster() != null).collect(Collectors.toCollection(HashSet::new));

        Map map = new HashMap();

        if (missions.size() == 0)
            throw new RuntimeException("restart system");

        draw(users, missions, map, true);
        draw(users, missions, map, false);

        userService.save(users);
    }

    private Set<User> draw(Set<User> users, Set<Mission> missions, Map map, boolean clear) {
        users.forEach(user -> {
            if (user.getChampions().size() == 0)
                throw new RuntimeException("restart system");

            List<Mission> oldMissions = new LinkedList<>(user.getMissions());
            if (oldMissions.size() == 0)
                oldMissions = new LinkedList<>();

            if (clear)
                user.getMissions().clear();

            List<Mission> missionForSpecifiedLevel = new LinkedList<>(getAllMissionsForSpecificLevel(missions, map, user.getUserChampionHighestLevel()));
            while (user.getMissions().size() < 3 && missionForSpecifiedLevel.size() > 0) {
                Mission mission = null;
                try {
                    mission = missionForSpecifiedLevel.get(rand.nextInt(missionForSpecifiedLevel.size() > 0 ? missionForSpecifiedLevel.size() : 1));

                    if (mission.getMonster().getLevel() > user.getUserChampionHighestLevel())
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

    private List<Mission> getAllMissionsForSpecificLevel(Set<Mission> missions, Map map, long level) {
        if (map.get(level) == null)
            map.put(level, missions.stream().filter(x -> x.getMonster().getLevel() <= level && x.getMonster().getLevel() >= level - SystemVariablesManager.MISSION_MONSTER_LEVEL_UNDER_CHAMPION_LEVEL).collect(Collectors.toList()));

        return (List<Mission>) map.get(level);
    }
}
