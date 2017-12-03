package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.ChampionRepository;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.other.managers.ChampionLevelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedList;

@Service
@Transactional
public class ChampionService {
    @Autowired
    private ChampionRepository repository;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;

    public void save(Champion champion) {
        if (champion != null)
            saveOperation(champion);
    }

    public void save(LinkedList<Champion> champions) {
        champions.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(Champion champion) {
        if (champion.getImage() != null)
            try {
                imageService.save(champion.getImage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (champion.getStatistic() != null)
            statisticService.save(champion.getStatistic());
        if (champion.getEquipment() != null)
            equipmentService.save(champion.getEquipment());

        champion = ChampionLevelManager.getChampionRealLevel(champion);
        repository.save(champion);
    }

    public Champion findOne(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Champion findOne(Champion champion) {
        try {
            return findOne(champion.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LinkedList<Champion> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(findOne(id));
    }

    public void delete(Champion image) {
        try {
            deleteOperation(image);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Champion> champions) {
        champions.forEach(
                x -> {
                    if (x != null)
                        deleteOperation(x);
                });
    }

    private void deleteOperation(Champion champion) {
        if (champion.getId() == null || findOne(champion.getId()) == null)
            return;

        LinkedList<Champion> champions = new LinkedList<>();
        userService.findAll().forEach(
                x -> {
                    x.getChampions().forEach(x1 ->
                            champions.add(x1)
                    );
                    champions.forEach(x.getChampions()::remove);
                    userService.save(x);
                    champions.clear();
                }
        );

        repository.deleteById(champion.getId());
    }
}
