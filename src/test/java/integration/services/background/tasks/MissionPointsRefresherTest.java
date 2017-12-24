package integration.services.background.tasks;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.background.tasks.MissionPointsRefresher;
import integration.config.IntegrationTestsConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class MissionPointsRefresherTest extends IntegrationTestsConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private MissionPointsRefresher missionPointsRefresher;

    @Test
    public void refreshMissionPointsForAllMission() {
        missionPointsRefresher.refreshMissionPointsForAllMission();

        LinkedList<User> users = userService.findAll();

        users.forEach(x -> {
            long level = x.getUserChampiongHighestLevel();

            if (level >= 0 && level <= 10)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_1_AND_10, level);
            else if (level >= 11 && level <= 20)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_11_AND_20, level);
            else if (level >= 21 && level <= 30)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_21_AND_30, level);
            else if (level >= 31 && level <= 40)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_31_AND_40, level);
            else if (level >= 41 && level <= 50)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_41_AND_50, level);
            else if (level >= 51 && level <= 60)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_51_AND_60, level);
            else if (level >= 61 && level <= 70)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_61_AND_70, level);
            else if (level >= 71 && level <= 80)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_71_AND_80, level);
            else if (level >= 81 && level <= 90)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_81_AND_90, level);
            else if (level >= 91 && level <= 100)
                assertEquals(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_91_AND_100, level);
        });
    }
}