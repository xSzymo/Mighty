package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.DungeonFightRepository;
import game.mightywarriors.data.tables.DungeonFight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class DungeonFightService {
    @Autowired
    private DungeonFightRepository repository;

    public void save(DungeonFight dungeonFight) {
        if (dungeonFight != null)
            saveOperation(dungeonFight);
    }

    public void save(Set<DungeonFight> dungeonFights) {
        dungeonFights.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(DungeonFight dungeonFight) {
        if (dungeonFight.getBlockUntil() == null)
            return;

        repository.save(dungeonFight);
    }

    public DungeonFight find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public DungeonFight find(DungeonFight dungeonFight) {
        try {
            return find(dungeonFight.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public DungeonFight findByUserId(long id) {
        try {
            return repository.findByUserId(id);
        } catch (Exception e) {
            return null;
        }
    }


    public HashSet<DungeonFight> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(find(id));
    }

    public void delete(DungeonFight dungeonFight) {
        try {
            deleteOperation(dungeonFight);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<DungeonFight> dungeonFights) {
        dungeonFights.forEach(
                x -> {
                    if (x != null)
                        deleteOperation(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(DungeonFight dungeonFight) {
        if (dungeonFight.getId() == null || find(dungeonFight.getId()) == null)
            return;

        repository.deleteById(dungeonFight.getId());
    }
}
