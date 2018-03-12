package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.UserRepository;
import game.mightywarriors.data.services.utilities.UserServiceUtility;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.services.background.tasks.ItemDrawer;
import game.mightywarriors.services.background.tasks.MissionAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository repository;
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
    private ChatService chatService;
    @Autowired
    private ItemDrawer itemDrawer;
    @Autowired
    private UserDungeonService userDungeonService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RoleService roleService;


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
        users.stream().filter(Objects::nonNull).forEach(this::saveOperation);
    }

    private void saveOperation(User user) {
        User foundUserWithSameLogin = find(user.getLogin());

        try {
            user = userServiceUtility.updateObjectsFromRelations(user);
            user = userServiceUtility.setToken(user, foundUserWithSameLogin);
            user = userServiceUtility.initializeBasicVariablesForNewUser(user, foundUserWithSameLogin);
            user = userServiceUtility.setDungeonIfAnyOfChampionsHaveEnoughLevel(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        repository.save(user);

        if (foundUserWithSameLogin == null) {
            if (user.getChampions().size() > 0) {
                if (user.getMissions().size() < 3)
                    missionAssigner.assignNewMissionForUsers(user.getId());

                if (user.getShop().getItems().size() < 1)
                    itemDrawer.drawItemsForUser(user.getId());
            }
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
            e.printStackTrace();
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

    public User findByEmail(String email) {
        try {
            return repository.findByEMail(email);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public User findByCodeToEnableAccount(String code) {
        try {
            return repository.findByCodeToEnableAccount(code);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Set<String> findAllCodesToEnableAccount() {
        try {
            return repository.findAllCodesToEnableAccount();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Set<AuthorizationCode> findAllAuthorizationCodes() {
        try {
            return repository.findAllAuthorizationCodes();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public User findByUserDungeonId(long id) {
        try {
            return repository.findByDungeonId(id);
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

    public void delete(String login) {
        deleteOperation(find(login));
    }

    public void delete(User user) {
        try {
            deleteOperation(user);
        } catch (NullPointerException e) {

        }
    }

    public void delete(HashSet<User> users) {
        users.forEach(this::delete);
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
        if (user.getDungeon() != null)
            userDungeonService.delete(user.getDungeon());
        if (user.getChats() != null)
            chatService.delete(user.getChats());
        if (user.getRole() != null) {
            Role role = roleService.find(user.getRole());
            role.getUsers().remove(repository.findByLogin(user.getLogin()));
            roleService.save(role);
        }

        messageService.find(user.getLogin()).forEach(messageService::delete);
        adminService.find(user.getLogin()).forEach(adminService::delete);
        user.getChampions().forEach(championService::delete);

        repository.deleteById(user.getId());
        rankingService.delete(user.getLogin());
    }

    public void changeNick(long userId, String newLogin) throws Exception {
        User user = find(userId);
        if (find(newLogin) != null)
            throw new Exception("login already taken");

        HashSet<Admin> admins = adminService.find(user.getLogin());
        admins.forEach(admin -> admin.setLogin(newLogin));
        adminService.save(admins);

        LinkedList<Message> messages = messageService.find(user.getLogin());
        messages.forEach(message -> message.setLogin(newLogin));
        messageService.save(messages);

        repository.updateLogin(newLogin, user.getId());
    }

    @Transactional
    public void removeChat(long userId, long chatId) throws NoSuchElementException {
        User user = find(userId);
        if (user != null) {
            Optional<Chat> myChat = user.getChats().stream().filter(x -> x.getId().equals(chatId)).findFirst();
            if (myChat.isPresent()) {
                Chat chat = myChat.get();

                user.getChats().remove(chat);
                chat.getUsers().remove(user);
                Optional<Admin> first = chat.getAdmins().stream().filter(x -> x.getLogin().equals(user.getLogin())).findFirst();
                first.ifPresent(admin -> chat.getAdmins().remove(admin));

                save(user);

                if (chat.getUsers().size() == 0)
                    chatService.delete(chat.getId());
            }
        }
    }
}
