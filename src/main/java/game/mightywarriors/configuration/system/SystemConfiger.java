package game.mightywarriors.configuration.system;

import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Division;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.League;
import game.mightywarriors.services.backgroundTasks.DivisionAssinger;
import game.mightywarriors.services.backgroundTasks.ItemDrawer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class SystemConfiger {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemDrawer itemDrawer;
    @Autowired
    private DivisionService divisionService;
    @Autowired
    private DivisionAssinger divisionAssinger;

    @PostConstruct
    public void configSystemAtStartApp() {
        createStandardDivisionsIfNotExist();
        addAllTokensFromDataBaseToCollectionInSystemVariableManager();

        updateDivisionForUsers();
        drawingItemsForUserEveryDay();
    }

    private void updateDivisionForUsers() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> divisionAssinger.assignUsersDivisions(), 0, SystemVariablesManager.HOW_MANY_HOURS_BETWEEN_UPDATE_DIVISIONS, TimeUnit.HOURS);
    }

    private void drawingItemsForUserEveryDay() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> itemDrawer.drawItemsForUser(), 0, SystemVariablesManager.HOW_MANY_HOURS_BETWEEN_NEXT_DRAW_ITEMS, TimeUnit.HOURS);
    }

    private void addAllTokensFromDataBaseToCollectionInSystemVariableManager() {
        LinkedList<User> all = userService.findAll();
        all.forEach(x -> SystemVariablesManager.JWTTokenCollection.add(x.getTokenCode()));
    }

    private void createStandardDivisionsIfNotExist() {
        if (divisionService.findAll().size() > 0)
            return;

        divisionService.save(new Division(League.CHALLENGER));
        divisionService.save(new Division(League.DIAMOND));
        divisionService.save(new Division(League.GOLD));
        divisionService.save(new Division(League.SILVER));
        divisionService.save(new Division(League.BRONZE));
        divisionService.save(new Division(League.WOOD));
    }
}
