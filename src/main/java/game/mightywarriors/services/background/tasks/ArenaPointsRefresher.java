package game.mightywarriors.services.background.tasks;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;

@Service
public class ArenaPointsRefresher {
    @Autowired
    private UserService userService;

    @Transactional
    public synchronized void refreshArenaPointsForAllUsers() {
        HashSet<User> users = userService.findAll();

        users.forEach(x -> x.setArenaPoints(SystemVariablesManager.ARENA_POINTS));

        userService.save(users);
    }
}
