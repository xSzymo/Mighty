package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.UserRepository;
import game.mightywarriors.data.services.utilities.UserServiceUtility;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.Inventory;
import game.mightywarriors.data.tables.Shop;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.background.tasks.ItemDrawer;
import game.mightywarriors.services.background.tasks.MissionAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private MissionService missionService;
    @Autowired
    private RankingService rankingService;
    @Autowired
    private UserServiceUtility userServiceUtility;
    @Autowired
    private MissionAssigner missionAssigner;
    @Autowired
    private ShopService shopService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ChampionService championService;
    @Autowired
    private DivisionService divisionService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ItemDrawer itemDrawer;

    public void save(User user) {
        if (user != null) {
            try {
                saveOperation(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void save(Set<User> users) {
        users.stream().filter(x -> x != null).forEach(this::saveOperation);
    }

    private void saveOperation(User user) {
        User foundUserWithSameLogin = find(user.getLogin());

        try {
            user = userServiceUtility.updateObjectsFromRelations(user);
            user = userServiceUtility.setToken(user, foundUserWithSameLogin);
            user = userServiceUtility.initializeBasicVariablesForNewUser(user, foundUserWithSameLogin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        repository.save(user);

        if (foundUserWithSameLogin == null) {
            if (user.getMissions().size() < 3)
                missionAssigner.assignNewMissionForUsers(user.getId());

            if (user.getShop().getItems().size() < 10)
                itemDrawer.drawItemsForUser(user.getId());
        }
    }

    public User find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public User find(String login) {
        try {
            return repository.findByLogin(login);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public User find(Shop shop) {
        try {
            return repository.findByShop(shop);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public User find(Inventory inventory) {
        try {
            return repository.findByInventory(inventory);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public User find(User user) {
        try {
            return find(user.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<User> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(find(id));
    }

    public void delete(User user) {
        try {
            deleteOperation(user);
        } catch (NullPointerException e) {

        }
    }

    public void delete(HashSet<User> users) {
        users.forEach(
                x -> {
                    if (x != null)
                        deleteOperation(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(User user) {
        if (user.getId() == 0 || find(user.getId()) == null)
            return;

        user.getMissions().clear();

        if (user.getShop() != null)
            shopService.delete(user.getShop());
        if (user.getInventory() != null)
            inventoryService.delete(user.getInventory());

        user.getChampions().forEach(championService::delete);
        rankingService.delete(user.getLogin());

        repository.deleteById(user.getId());
    }

    @Transactional
    public void removeChat(long userId, long chatId) {
        User user = find(userId);
        if (user != null) {
            Chat chat = user.getChats().stream().filter(x -> x.getId().equals(chatId)).findFirst().get();
            if (chat != null) {
                user.getChats().remove(chat);
                chat.getUsers().remove(user);
                save(user);
            }
        }
    }
}
