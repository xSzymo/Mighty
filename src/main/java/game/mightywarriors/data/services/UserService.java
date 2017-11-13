package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.UserRepository;
import game.mightywarriors.data.tables.Champion;
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

    public void save(User user) {
        if (user != null)
            saveOperation(user);
    }

    public void save(LinkedList<User> users) {
        users.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(User user) {
        if (user.getMissions() != null)
            missionService.save(user.getMissions());
        if (user.getShop() != null)
            shopService.save(user.getShop());
        if (user.getChampions() != null)
            for (Champion champion : user.getChampions())
                if (champion != null)
                    championService.save(champion);

        if (user.getImage() != null)
            try {
                imageService.save((user.getImage()));
            } catch (Exception e) {
                e.printStackTrace();
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

    private void deleteOperation(User user) {
        if (user.getId() == null || findOne(user.getId()) == null)
            return;

        user.getMissions().clear();
        user.getMissions().forEach(missionService::delete);
        if (user.getShop() != null)
            shopService.delete(user.getShop());
        repository.deleteById(user.getId());
    }
}
