package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.UserDungeonRepository;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.data.tables.UserDungeon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserDungeonService {
    @Autowired
    private UserDungeonRepository repository;
    @Autowired
    private DungeonService dungeonService;
    @Autowired
    private UserService userService;

    public void save(UserDungeon userDungeon) {
        if (userDungeon != null)
            saveOperation(userDungeon);
    }

    public void save(Set<UserDungeon> userDungeons) {
        userDungeons.forEach(this::save);
    }

    private void saveOperation(UserDungeon userDungeon) {
        dungeonService.save(userDungeon.getDungeon());
        repository.save(userDungeon);
    }

    public UserDungeon find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public UserDungeon find(UserDungeon userDungeon) {
        try {
            return find(userDungeon.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<UserDungeon> findAll() {
        return repository.findAll();
    }

    public UserDungeon findByDungeonId(long id) {
        try {
            return repository.findByDungeonId(id);
        } catch (NullPointerException e) {
            return null;
        }
    }


    public void delete(long id) {
        deleteOperation(find(id));
    }

    public void delete(UserDungeon userDungeon) {
        try {
            deleteOperation(userDungeon);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<UserDungeon> userDungeons) {
        userDungeons.forEach(
                x -> {
                    if (x != null)
                        deleteOperation(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(UserDungeon userDungeon) {
        if (userDungeon.getId() == null || find(userDungeon.getId()) == null)
            return;

        userDungeon.setDungeon(null);
        save(userDungeon);

        User usersUserDungeon = userService.findByUserDungeonId(userDungeon.getId());
        if (usersUserDungeon != null) {
            usersUserDungeon.setDungeon(null);
            userService.save(usersUserDungeon);
        }

        repository.deleteById(userDungeon.getId());
    }
}
