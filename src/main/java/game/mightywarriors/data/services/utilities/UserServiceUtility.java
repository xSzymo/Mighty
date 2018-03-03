package game.mightywarriors.data.services.utilities;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceUtility {
    @Autowired
    private MissionService missionService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ChampionService championService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private RankingService rankingService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private GuildService guildService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserDungeonService userDungeonService;
    @Autowired
    private DungeonService dungeonService;

    public User setToken(User user, User foundUser) throws Exception {
        if (user.isNewToken()) {
            String token = user.getTokenCode();
            String tokenAfterEncode = SystemVariablesManager.ENCODER_DB.encode(token);

            user.setTokenCode(tokenAfterEncode);

        } else if (user.getTokenCode() != null) {

            if (foundUser != null) {
                String foundUserToken = SystemVariablesManager.DECO4DER_DB.decode(foundUser.getTokenCode());
                String token = user.getTokenCode();
                String tokenAfterDBEncode = SystemVariablesManager.DECO4DER_DB.decode(token);
                String tokenAfterJSONEncode = SystemVariablesManager.DECODER_JSON.decode(token);
                if (!token.equals(foundUserToken)) {
                    user.setTokenCode(SystemVariablesManager.ENCODER_DB.encode(foundUserToken));
                } else if (!tokenAfterDBEncode.equals(foundUserToken)) {
                    user.setTokenCode(SystemVariablesManager.ENCODER_DB.encode(foundUserToken));
                } else if (!tokenAfterJSONEncode.equals(foundUserToken)) {
                    user.setTokenCode(SystemVariablesManager.ENCODER_DB.encode(foundUserToken));
                } else
                    throw new Exception("oh no");
            }
        } else if (foundUser != null) {
            user.setTokenCode(null);
        }

        return user;
    }

    public User initializeBasicVariablesForNewUser(User user, User foundUser) {
        if (foundUser == null) {
            Ranking ranking = new Ranking(user.getLogin());
            rankingService.save(ranking);

            user.setArenaPoints(SystemVariablesManager.ARENA_POINTS);
            user.setMissionPoints(SystemVariablesManager.POINTS_MISSIONS_BETWEEN_LEVEL_1_AND_10);
            user.setDungeonPoints(SystemVariablesManager.DUNGEON_POINTS);

            if (user.getRole() == null) {
                user.setRole(roleService.find("user"));
            }
        }

        if (user.getInventory() == null) {
            Inventory inventory = new Inventory();
            inventoryService.save(inventory);
            user.setInventory(inventory);
        }

        if (user.getShop() == null) {
            Shop shop = new Shop();
            user.setShop(shop);
            shopService.save(shop);
        }

        return user;
    }

    public User updateObjectsFromRelations(User user) {
        if (user.getMissions() != null) {
            HashSet<Mission> missions = new HashSet<>();
            for (Mission mission : user.getMissions())
                if (mission != null) {
                    missionService.save(mission);
                    missions.add(mission);
                }
            user.setMissions(missions);
        }

        if (user.getChampions() != null) {
            if (user.getChampions().size() == 0) {
                user.setChampions(new HashSet<>());
            } else {
                HashSet<Champion> champions = new HashSet<>();
                for (Champion champion : user.getChampions())
                    if (champion != null) {
                        championService.save(champion);
                        champions.add(champion);
                    }
                user.setChampions(champions);
            }
        }

        if (user.getInventory() != null)
            inventoryService.save(user.getInventory());

        if (user.getShop() != null)
            shopService.save(user.getShop());

        if (user.getRole() != null)
            roleService.save(user.getRole());

        if (user.getGuild() != null)
            guildService.save(user.getGuild());

        if (user.getDungeon() != null)
            userDungeonService.save(user.getDungeon());

        if (user.getImage() != null) {
            try {
                imageService.save((user.getImage()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        user.getChats().forEach(x -> {
            if (!x.getUsers().contains(user))
                x.getUsers().add(user);
            chatService.save(x);
        });

        return user;
    }

    public User setDungeonIfAnyOfChampionsHaveEnoughLevel(User user) {
        if (user.getDungeon() == null) {
            if (user.getUserChampionHighestLevel() >= SystemVariablesManager.CHAMPION_LEVEL_FOR_FIRST_DUNGEON) {
                Dungeon dungeon = dungeonService.findByNumber(1);

                UserDungeon userDungeon = new UserDungeon();
                userDungeon.setDungeon(dungeon);
                userDungeon.setCurrentFloor(SystemVariablesManager.MAX_FLOORS_PER_DUNGEON);
                userDungeonService.save(userDungeon);

                user.setDungeon(userDungeon);
            }
        }
        return user;
    }
}
