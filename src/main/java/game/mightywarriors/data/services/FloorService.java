package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.FloorRepository;
import game.mightywarriors.data.tables.Dungeon;
import game.mightywarriors.data.tables.Floor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class FloorService {
    @Autowired
    private FloorRepository repository;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private DungeonService dungeonService;

    public void save(Floor floor) {
        if (floor != null)
            saveOperation(floor);
    }

    public void save(Set<Floor> floors) {
        floors.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(Floor floor) {
        if (floor.getMonsters() != null)
            monsterService.save(floor.getMonsters());
        if (floor.getItem() != null)
            itemService.save(floor.getItem());
        if (floor.getImage() != null)
            try {
                imageService.save(floor.getImage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        repository.save(floor);
    }

    public Floor find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Floor find(Floor floor) {
        try {
            return find(floor.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Floor> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(find(id));
    }

    public void delete(Floor floor) {
        try {
            deleteOperation(floor);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Floor> floors) {
        floors.forEach(
                x -> {
                    if (x != null)
                        deleteOperation(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Floor floor) {
        if (floor.getId() == null || find(floor.getId()) == null)
            return;

        floor.setItem(null);
        repository.save(floor);

        HashSet<Dungeon> collect = dungeonService.findAll().stream().filter(x -> x.getFloors().stream().anyMatch(dungeonFloor -> dungeonFloor.getId().equals(floor.getId()))).collect(Collectors.toCollection(HashSet::new));
        collect.forEach(x -> x.getFloors().remove(floor));
        dungeonService.save(collect);

        repository.deleteById(floor.getId());
    }
}
