package game.mightywarriors.configuration.system;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.DrawerItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class SystemRunAtStart {
    @Autowired
    private UserService userService;
    @Autowired
    private DrawerItemService drawerItemService;

    @PostConstruct
    public void runAtStart() {
        addAllTokensFromDataBaseToCollectionInSystemVariableManager();
        drawingItemsForUserEveryDay();
    }

    private void drawingItemsForUserEveryDay() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> drawerItemService.drawItemsForUser() , 0, SystemVariablesManager.HOW_MANY_HOURS_BEETWEN_NEXT_DRAW_ITEMS, TimeUnit.HOURS);
    }

    private void addAllTokensFromDataBaseToCollectionInSystemVariableManager() {
        LinkedList<User> all = userService.findAll();
        all.forEach(x -> SystemVariablesManager.JWTTokenCollection.add(x.getTokenCode()));
    }
}
