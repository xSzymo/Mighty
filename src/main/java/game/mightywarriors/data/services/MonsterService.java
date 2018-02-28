package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.MissionRepository;
import game.mightywarriors.data.repositories.MonsterRepository;
import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class MonsterService {
    @Autowired
    private MonsterRepository repository;
    @Autowired
    private MissionService missionRepository;
    @Autowired
    private MissionRepository missionRepository1;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private ImageService imageService;

    public void save(Monster monster) {
        if (monster != null)
            saveOperation(monster);
    }

    public void save(Set<Monster> monster) {
        monster.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(Monster monster) {
        if (monster.getStatistic() != null)
            statisticService.save(monster.getStatistic());
        if (monster.getImageLight() != null)
            try {
                if (monster.getImageLight() != null)
                    imageService.save(monster.getImageLight());

            } catch (Exception e) {
                e.printStackTrace();
            }
        repository.save(monster);
    }

    public Monster find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Monster find(Monster monster) {
        try {
            return find(monster.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Monster> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(find(id));
    }

    public void delete(Monster monster) {
        try {
            deleteOperation(monster);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Monster> monsters) {
        monsters.forEach(
                x -> {
                    if (x != null)
                        deleteOperation(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Monster monster) {
        Long id = monster.getId();
        if (id == null || find(id) == null)
            return;


        Mission one = missionRepository.find(monster);
        if (one != null) {
            one.setMonster(null);
            missionRepository.save(one);
        }

        repository.deleteById(id);
    }
}
