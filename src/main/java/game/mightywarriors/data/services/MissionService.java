package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.MissionRepository;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

@Service
@Transactional
public class MissionService {
    @Autowired
    private MissionRepository repository;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private UserService userService;

    public void save(Mission mission) {
        if (mission != null)
            saveOperation(mission);
    }

    public void save(Collection<Mission> missions) {
        missions.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(Mission mission) {
        if (mission.getMonster() != null)
            monsterService.save(mission.getMonster());
        repository.save(mission);
    }

    public Mission findOne(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Mission findOne(Mission mission) {
        try {
            return findOne(mission.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Mission findOne(Monster monster) {
        try {
            return repository.findByMonster(monster);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Mission> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(findOne(id));
    }

    public void delete(Mission image) {
        try {
            deleteOperation(image);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Mission> missions) {
        missions.forEach(
                x -> {
                    if (x != null)
                        deleteOperation(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Mission mission) {
        if (mission.getId() == null || findOne(mission.getId()) == null)
            return;

        userService.findAll().forEach(
                x -> {
                    if (x.getMissions() != null) {
                        Iterator<Mission> iterator = new HashSet<>(x.getMissions()).iterator();
                        while (iterator.hasNext()) {
                            Mission mission1 = iterator.next();
                            if (mission1.getId().equals(mission.getId())) {
                                x.getMissions().remove(mission1);
                                userService.save(x);
                            }
                        }
                    }
                }
        );

        repository.deleteById(mission.getId());
    }
}
