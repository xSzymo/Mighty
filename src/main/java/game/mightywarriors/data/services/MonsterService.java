package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.MissionRepository;
import game.mightywarriors.data.repositories.MonsterRepository;
import game.mightywarriors.data.tables.Monster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedList;

@Service
@Transactional
public class MonsterService {
    @Autowired
    private MonsterRepository repository;
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private ImageService imageService;

    public void save(Monster monster) {
        if (monster != null)
            saveOperation(monster);
    }

    public void save(LinkedList<Monster> monster) {
        monster.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(Monster monster) {
        if (monster.getStatistic() != null)
            statisticService.save(monster.getStatistic());
        if (monster.getImage() != null)
            try {
                if (imageService.findByUrl(monster.getImage().getUrl()) == null)
                    imageService.save(monster.getImage());
                else
                    monster.setImage(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        repository.save(monster);
    }

    public Monster findOne(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Monster findOne(Monster monster) {
        try {
            return findOne(monster.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LinkedList<Monster> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(findOne(id));
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

    private void deleteOperation(Monster monster) {
        if (monster.getId() == null || findOne(monster.getId()) == null)
            return;

        missionRepository.findAll().forEach(
                x -> {
                    if (x.getMonster() != null)
                        if (x.getMonster().getId().equals(monster.getId())) {
                            x.setMonster(null);
                            missionRepository.save(x);
                        }
                }
        );

        if (monster.getStatistic() != null)
            statisticService.delete(monster.getStatistic());
        if (monster.getImage() != null)
            imageService.delete(monster.getImage());

        repository.deleteById(monster.getId());
    }
}
