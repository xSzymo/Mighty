package integration.services.background.tasks;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.background.tasks.MissionPointsRefresher;
import integration.config.IntegrationTestsConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class MissionPointsRefresherTest extends IntegrationTestsConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private MissionPointsRefresher missionPointsRefresher;

    @Before
    public void setUp() {
        int level = 10;
        for (int i = 2; i < 100; i += 2) {
            if (i == level)
                level += 10;

            User user = new User("" + i, "", "").addChampion(new Champion().setLevel(level));
            userService.save(user);
        }
    }

    @Test
    @Transactional
    public void refreshMissionPointsForAllMission() {
        Set<User> users = userService.findAll();
        users.forEach(x -> x.setMissionPoints(2));
        userService.save(users);

        missionPointsRefresher.refreshMissionPointsForAllMission();

        userService.findAll().forEach(x -> {
            long level = x.getUserChampiongHighestLevel();

            if (level >= 0 && level <= 10)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_1_AND_10, x.getMissionPoints());
            else if (level >= 11 && level <= 20)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_11_AND_20, x.getMissionPoints());
            else if (level >= 21 && level <= 30)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_21_AND_30, x.getMissionPoints());
            else if (level >= 31 && level <= 40)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_31_AND_40, x.getMissionPoints());
            else if (level >= 41 && level <= 50)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_41_AND_50, x.getMissionPoints());
            else if (level >= 51 && level <= 60)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_51_AND_60, x.getMissionPoints());
            else if (level >= 61 && level <= 70)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_61_AND_70, x.getMissionPoints());
            else if (level >= 71 && level <= 80)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_71_AND_80, x.getMissionPoints());
            else if (level >= 81 && level <= 90)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_81_AND_90, x.getMissionPoints());
            else if (level >= 91 && level <= 100)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_91_AND_100, x.getMissionPoints());
        });
    }
}