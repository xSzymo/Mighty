package game.mightywarriors.services.bookmarks.dungeon;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.DungeonFightService;
import game.mightywarriors.data.services.DungeonService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Dungeon;
import game.mightywarriors.data.tables.DungeonFight;
import game.mightywarriors.data.tables.Floor;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.bookmarks.utilities.DungeonHelper;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class DungeonUtility {
    @Autowired
    private UserService userService;
    @Autowired
    private DungeonFightService dungeonFightService;
    @Autowired
    private DungeonService dungeonService;
    @Autowired
    private DungeonHelper dungeonHelper;

    private static final int ONE_SECOND = 1000;

    public void prepareNewDungeonFight(User user) throws Exception {
        DungeonFight dungeonFight = new DungeonFight();
        Floor floor = dungeonHelper.getCurrentFloor(user);

        Timestamp blockDate = new Timestamp(System.currentTimeMillis() + (floor.getDuration() * ONE_SECOND));
        user.getChampions().forEach(x -> x.setBlockUntil(blockDate));
        dungeonFight.setBlockUntil(blockDate);
        dungeonFight.setUser(user);

        dungeonFightService.save(dungeonFight);
        userService.save(user);
    }

    public FightResult getThingsDoneAfterFight(User user, DungeonFight dungeonFight, FightResult fight, boolean wonFight) throws Exception {
        Dungeon dungeon = user.getDungeon().getDungeon();
        Floor floor = dungeonHelper.getCurrentFloor(user);

        if (wonFight) {
            long missionExperience = floor.getExperience();
            BigDecimal missionGold = floor.getGold();
            user.getInventory().addItem(floor.getItem());

            long exp = missionExperience / user.getChampions().size();
            user.getChampions().forEach(x -> x.addExperience(exp));
            user.addGold(missionGold);

            fight.setExperience(missionExperience);
            fight.setGold(missionGold);
            fight.setItem(floor.getItem());

            if (floor.getFloor() == 1) {
                try {
                    user.getDungeon().setDungeon(dungeonService.findByNumber(dungeon.getNumber() + 1));
                    user.getDungeon().setCurrentFloor(SystemVariablesManager.MAX_FLOORS_PER_DUNGEON);
                    dungeonHelper.throwExceptionIf_DungeonsFloorsAreNotSetProperly(user);
                } catch (Exception e) {
                    e.printStackTrace();//what should program do when user get dungeon with not properly floors?
                    user.getDungeon().setDungeon(dungeonService.findByNumber(dungeon.getNumber() + 2));
                }
            } else {
                user.getDungeon().setCurrentFloor(user.getDungeon().getCurrentFloor() - 1);
            }
        }

        user.setDungeonPoints(user.getDungeonPoints() - 1);
        user.getChampions().forEach(x -> x.setBlockUntil(null));

        dungeonFightService.delete(dungeonFight);
        userService.save(user);

        return fight;
    }
}
