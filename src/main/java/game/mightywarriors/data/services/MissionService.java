package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.MissionRepository;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class MissionService {
    @Autowired
    private MissionRepository repository;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;

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
        if (mission.getImage() != null)
            try {
                imageService.save(mission.getImage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        repository.save(mission);
    }

    public Mission find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Mission find(Mission mission) {
        try {
            return find(mission.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Mission find(Monster monster) {
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
        deleteOperation(find(id));
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
        if (mission.getId() == null || find(mission.getId()) == null)
            return;

        repository.deleteById(mission.getId());
    }
}
