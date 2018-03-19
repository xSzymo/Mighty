package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.DungeonRepository;
import game.mightywarriors.data.tables.Dungeon;
import game.mightywarriors.data.tables.UserDungeon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class DungeonService {
    @Autowired
    private DungeonRepository repository;
    @Autowired
    private FloorService floorService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserDungeonService userDungeonService;

    public void save(Dungeon dungeon) {
        if (dungeon != null)
            saveOperation(dungeon);
    }

    public void save(Set<Dungeon> dungeons) {
        dungeons.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(Dungeon dungeon) {

        if (dungeon.getImage() != null)
            try {
                imageService.save(dungeon.getImage());
            } catch (Exception e) {
                e.printStackTrace();
            }


        dungeon.getFloors().forEach(floorService::save);
        repository.save(dungeon);
    }

    public Dungeon find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Dungeon find(Dungeon dungeon) {
        try {
            return find(dungeon.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Dungeon findByNumber(int number) {
        try {
            return repository.findByStage(number);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Dungeon> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(find(id));
    }

    public void delete(Dungeon dungeon) {
        try {
            deleteOperation(dungeon);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Dungeon> dungeons) {
        dungeons.forEach(
                x -> {
                    if (x != null)
                        deleteOperation(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Dungeon dungeon) {
        if (dungeon.getId() == null || find(dungeon.getId()) == null)
            return;

        Set<UserDungeon> usersDungeons = userDungeonService.findByDungeonId(dungeon.getId());
        for (UserDungeon x : usersDungeons) {
            x.setDungeon(null);
            userDungeonService.save(x);
        }

        dungeon.getFloors().forEach(floorService::delete);
        repository.deleteById(dungeon.getId());
    }
}
