package game.mightywarriors.configuration.system.system;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.DivisionService;
import game.mightywarriors.data.services.RoleService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Division;
import game.mightywarriors.data.tables.Role;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.League;
import game.mightywarriors.services.background.tasks.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class SystemConfiger {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ItemDrawer itemDrawer;
    @Autowired
    private UserCleaner userCleaner;
    @Autowired
    private DivisionService divisionService;
    @Autowired
    private DivisionAssigner divisionAssigner;
    @Autowired
    private MissionPointsRefresher missionPointsRefresher;
    @Autowired
    private ArenaPointsRefresher arenaPointsRefresher;
    @Autowired
    private DungeonPointsRefresher dungeonPointsRefresher;

    @PostConstruct
    public void configSystemAtStartApp() {
        createStandardDivisionsIfNotExist();
        createStandardRolesIfNotExist();
        addAllTokensFromDataBaseToCollectionInSystemVariableManager();

        updateDivisionForUsers();
        refreshMissionPoints();
        refreshArenaPoints();
        refreshDungeonPoints();
        drawingItemsForUserEveryDay();
        deleteAllUsersWithExpiredTokens();
    }

    private void updateDivisionForUsers() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> divisionAssigner.assignUsersDivisions(), 0, SystemVariablesManager.HOW_MANY_HOURS_BETWEEN_UPDATE_DIVISIONS, TimeUnit.HOURS);
    }

    private void refreshMissionPoints() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> missionPointsRefresher.refreshMissionPointsForAllMission(), 0, SystemVariablesManager.HOW_MANY_HOURS_BETWEEN_REFRESH_MISSION_POINTS, TimeUnit.HOURS);
    }

    private void refreshArenaPoints() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> arenaPointsRefresher.refreshArenaPointsForAllUsers(), 0, SystemVariablesManager.HOW_MANY_HOURS_BETWEEN_REFRESH_ARENA_POINTS, TimeUnit.HOURS);
    }

    private void refreshDungeonPoints() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> dungeonPointsRefresher.refreshDungeonPointsForAllUsers(), 0, SystemVariablesManager.HOW_MANY_HOURS_BETWEEN_REFRESH_DUNGEON_POINT, TimeUnit.HOURS);
    }

    private void drawingItemsForUserEveryDay() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> itemDrawer.drawItemsForUser(), 0, SystemVariablesManager.HOW_MANY_HOURS_BETWEEN_NEXT_DRAW_ITEMS, TimeUnit.HOURS);
    }

    private void deleteAllUsersWithExpiredTokens() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> userCleaner.deleteAllUsersWithExpiredTokens(), 0, SystemVariablesManager.HOW_MANY_HOURS_TO_DELETE_USERS_WITH_EXPIRED_TOKENS, TimeUnit.HOURS);
    }

    private void addAllTokensFromDataBaseToCollectionInSystemVariableManager() {
        HashSet<User> all = userService.findAll();
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

    private void createStandardRolesIfNotExist() {
        if (roleService.findAll().size() > 0)
            return;

        roleService.save(new Role(game.mightywarriors.other.enums.GuildRole.OWNER.getRole()));
        roleService.save(new Role(game.mightywarriors.other.enums.GuildRole.MEMBER.getRole()));
        roleService.save(new Role("admin"));
        roleService.save(new Role("user"));
    }
}
