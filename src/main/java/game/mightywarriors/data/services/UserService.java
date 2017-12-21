package game.mightywarriors.data.services;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.repositories.UserRepository;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Shop;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository repository;
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

    public void save(User user) {
        if (user != null) {
            try {
                saveOperation(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void save(LinkedList<User> users) {
        users.forEach(
                x -> {
                    if (x != null) {
                        try {
                            saveOperation(x);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void saveOperation(User user) throws Exception {
        if (user.getMissions() != null)
            missionService.save(user.getMissions());

        if (user.getInventory() != null)
            inventoryService.save(user.getInventory());

        if (user.getChampions() != null)
            if (user.getChampions().size() == 0) {
                Champion champion = new Champion();
                user.addChampion(champion);
                championService.save(champion);
            } else {
                for (Champion champion : user.getChampions())
                    if (champion != null)
                        championService.save(champion);
            }

        if (user.getShop() != null)
            shopService.save(user.getShop());
        else {
            Shop shop = new Shop();
            user.setShop(shop);
            shopService.save(shop);
        }

        if (user.getImage() != null)
            try {
                imageService.save((user.getImage()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (user.isNewToken()) {
            String token = user.getTokenCode();
            String tokenAfterEncode = SystemVariablesManager.ENCODER_DB.encode(token);

            user.setTokenCode(tokenAfterEncode);

        } else if (user.getTokenCode() != null) {
            User found = findByLogin(user.getLogin());
            if (found != null) {
                String foundUserToken = SystemVariablesManager.DECO4DER_DB.decode(found.getTokenCode());
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
        } else {
            System.out.println("BE CAREFUL EMPTY TOKEN");
            user.setTokenCode(null);
        }
        repository.save(user);
    }

    public User findOne(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public User findByLogin(String login) {
        try {
            return repository.findByLogin(login);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public User findOne(User user) {
        try {
            return findOne(user.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LinkedList<User> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(findOne(id));
    }

    public void delete(User user) {
        try {
            deleteOperation(user);
        } catch (NullPointerException e) {

        }
    }

    public void delete(LinkedList<User> users) {
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
        if (user.getId() == 0 || findOne(user.getId()) == null)
            return;

        user.getMissions().clear();
        user.getMissions().forEach(missionService::delete);
//        if (user.getShop() != null)
//            shopService.delete(user.getShop());
//        if (user.getInventory() != null)
//            inventoryService.delete(user.getInventory());
        repository.deleteById(user.getId());
    }
}
