package game.mightywarriors.data.services;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.repositories.ChampionRepository;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Equipment;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.data.tables.Work;
import game.mightywarriors.other.managers.ChampionLevelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    @Autowired
    private WorkService workService;

    public void save(Champion champion) {
        if (champion != null)
            saveOperation(champion);
    }

    public void save(Set<Champion> champions) {
        champions.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(Champion champion) {
        Champion foundChampion = find(champion);

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

        if (foundChampion == null) {
            if (champion.getEquipment() == null) {
                Equipment equipment = new Equipment();
                equipmentService.save(equipment);
                champion.setEquipment(equipment);
            }

            if (champion.getStatistic() == null) {
                Statistic statistic = new Statistic(SystemVariablesManager.DEFAULT_STRENGTH_FOR_NEW_CHAMPION, SystemVariablesManager.DEFAULT_INTELLIGENCE_FOR_NEW_CHAMPION,
                        SystemVariablesManager.DEFAULT_VITALITY_FOR_NEW_CHAMPION, SystemVariablesManager.DEFAULT_CRITICAL_CHANCE_FOR_NEW_CHAMPION,
                        SystemVariablesManager.DEFAULT_ARMOR_FOR_NEW_CHAMPION, SystemVariablesManager.DEFAULT_MAGIC_RESIST_FOR_NEW_CHAMPION);
                statisticService.save(statistic);
                champion.setStatistic(statistic);
            }
        }

        champion = ChampionLevelManager.getChampionRealLevel(champion);
        repository.save(champion);
    }

    public Champion find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Champion find(Champion champion) {
        try {
            return find(champion.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Champion> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(find(id));
    }

    public void delete(Champion champion) {
        try {
            deleteOperation(champion);
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

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Champion champion) {
        if (champion.getId() == null || find(champion.getId()) == null)
            return;

        Equipment equipment = champion.getEquipment();
        if (equipment != null)
            equipmentService.delete(equipment);

        Work one = workService.find(champion);
        if (one != null)
            workService.delete(one);

        repository.deleteById(champion.getId());
    }
}
