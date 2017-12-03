package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.MissionRepository;
import game.mightywarriors.data.tables.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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

    public LinkedList<Mission> findAll() {
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
                        List<Mission> missions = new LinkedList<>(x.getMissions());
                        for (int i = 0, missionsSize = missions.size(); i < missionsSize; i++) {
                            Mission mission1 = missions.get(i);
                            if (mission1.getId().equals(mission.getId())) {
                                x.getMissions().remove(mission1);
                                userService.save(x);
                            }
                        }
                    }
                }
        );

        monsterService.delete(mission.getMonster());
        repository.deleteById(mission.getId());
    }
}
