package game.mightywarriors.services.background.tasks;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;

@Service
public class MissionPointsRefresher {
    private UserService userService;

    @Autowired
    public MissionPointsRefresher(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public synchronized void refreshMissionPointsForAllMission() {
        HashSet<User> users = userService.findAll();

        users.forEach(x -> {
            long level = x.getUserChampionHighestLevel();

            if (level >= 0 && level <= 10)
                x.setMissionPoints(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_1_AND_10);
            else if (level >= 11 && level <= 20)
                x.setMissionPoints(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_11_AND_20);
            else if (level >= 21 && level <= 30)
                x.setMissionPoints(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_21_AND_30);
            else if (level >= 31 && level <= 40)
                x.setMissionPoints(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_31_AND_40);
            else if (level >= 41 && level <= 50)
                x.setMissionPoints(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_41_AND_50);
            else if (level >= 51 && level <= 60)
                x.setMissionPoints(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_51_AND_60);
            else if (level >= 61 && level <= 70)
                x.setMissionPoints(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_61_AND_70);
            else if (level >= 71 && level <= 80)
                x.setMissionPoints(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_71_AND_80);
            else if (level >= 81 && level <= 90)
                x.setMissionPoints(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_81_AND_90);
            else if (level >= 91 && level <= 100)
                x.setMissionPoints(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_91_AND_100);
        });

        userService.save(users);
    }
}
