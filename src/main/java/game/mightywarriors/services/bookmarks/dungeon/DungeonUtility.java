package game.mightywarriors.services.bookmarks.dungeon;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.DungeonFightService;
import game.mightywarriors.data.services.DungeonService;
import game.mightywarriors.data.services.InventoryService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.services.bookmarks.utilities.DungeonHelper;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class DungeonUtility {
    private UserService userService;
    private DungeonFightService dungeonFightService;
    private DungeonService dungeonService;
    private DungeonHelper dungeonHelper;
    private InventoryService inventoryService;

    private static final int ONE_SECOND = 1000;

    @Autowired
    public DungeonUtility(DungeonFightService dungeonFightService, DungeonHelper dungeonHelper, UserService userService, DungeonService dungeonService, InventoryService inventoryService) {
        this.dungeonFightService = dungeonFightService;
        this.dungeonHelper = dungeonHelper;
        this.dungeonService = dungeonService;
        this.userService = userService;
        this.inventoryService = inventoryService;
    }

    public void prepareNewDungeonFight(User user) throws Exception {
        DungeonFight dungeonFight = new DungeonFight();
        Floor floor = dungeonHelper.getCurrentFloor(user);

        Timestamp blockDate = new Timestamp(System.currentTimeMillis() + (floor.getDuration() * ONE_SECOND));
        user.getChampions().forEach(x -> x.setBlockUntil(blockDate));
        dungeonFight.setBlockUntil(blockDate);
        dungeonFight.setUser(user);

        dungeonFightService.save(dungeonFight);
        user.setDungeonPoints(user.getDungeonPoints() - 1);
        userService.save(user);
    }

    public FightResult getThingsDoneAfterFight(User user, DungeonFight dungeonFight, FightResult fight, boolean wonFight) throws Exception {
        Dungeon dungeon = user.getDungeon().getDungeon();
        Floor floor = dungeonHelper.getCurrentFloor(user);

        if (wonFight) {
            long missionExperience = floor.getExperience();
            BigDecimal missionGold = floor.getGold();

            Inventory inventory = inventoryService.find(user.getInventory());
            inventory.addItem(floor.getItem());
            inventoryService.save(inventory);

            long exp = missionExperience / user.getChampions().size();
            user.getChampions().forEach(x -> x.addExperience(exp));
            user.addGold(missionGold);

            fight.setExperience(missionExperience);
            fight.setGold(missionGold);
            fight.setItem(floor.getItem());

            if (floor.getFloor() == 1) {
                try {
                    user.getDungeon().setDungeon(dungeonService.findByNumber(dungeon.getStage() + 1));
                    user.getDungeon().setCurrentFloor(SystemVariablesManager.MAX_FLOORS_PER_DUNGEON);
                    dungeonHelper.throwExceptionIf_DungeonsFloorsAreNotSetProperly(user);
                } catch (Exception e) {
                    e.printStackTrace();//what should program do when user get dungeon with not properly floors?
                    user.getDungeon().setDungeon(dungeonService.findByNumber(dungeon.getStage()));
                }
            } else {
                user.getDungeon().setCurrentFloor(user.getDungeon().getCurrentFloor() - 1);
            }
        }

        user.getChampions().forEach(x -> x.setBlockUntil(null));

        dungeonFightService.delete(dungeonFight);
        userService.save(user);

        return fight;
    }
}
