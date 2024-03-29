package game.mightywarriors.services.background.tasks;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;

@Service
public class DungeonPointsRefresher {
    private UserService userService;

    @Autowired
    public DungeonPointsRefresher(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public synchronized void refreshDungeonPointsForAllUsers() {
        HashSet<User> users = userService.findAll();

        users.forEach(x -> x.setDungeonPoints(SystemVariablesManager.DUNGEON_POINTS));

        userService.save(users);
    }
}
