package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.StatisticRepository;
import game.mightywarriors.data.tables.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class StatisticService {
    @Autowired
    private StatisticRepository repository;
    @Autowired
    private ChampionService championService;
    @Autowired
    private MonsterService monsterService;
    @Autowired
    private ItemService itemService;

    public void save(Statistic statistic) {
        if (statistic != null) {
            if (statistic.getArmor() == 0)
                statistic.setArmor(1);
            if (statistic.getCriticalChance() == 0)
                statistic.setCriticalChance(1);
            if (statistic.getIntelligence() == 0)
                statistic.setIntelligence(1);
            if (statistic.getMagicResist() == 0)
                statistic.setMagicResist(1);
            if (statistic.getStrength() == 0)
                statistic.setStrength(1);
            if (statistic.getVitality() == 0)
                statistic.setVitality(1);
            repository.save(statistic);
        }
    }

    public void save(Set<Statistic> statistics) {
        statistics.forEach(
                x -> {
                    if (x != null)
                        repository.save(x);
                });
    }

    public Statistic find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Statistic find(Statistic statistic) {
        try {
            return find(statistic.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Statistic> findAll() {
        return repository.findAll();
    }

    @Deprecated
    public void delete(long id) {
        deleteOperation(find(id));
    }

    @Deprecated
    public void delete(Statistic statistic) {
        try {
            deleteOperation(statistic);
        } catch (NullPointerException e) {

        }
    }

    @Deprecated
    public void delete(Collection<Statistic> statistics) {
        statistics.forEach(
                x -> {
                    if (x != null)
                        deleteOperation(x);
                });
    }

    @Deprecated
    public void deleteAll() {
        delete(findAll());
    }

    /**
     * This method should not be used anymore
     * There isn't case where You have to delete statistic
     * When You delete the relation monster, item or champion statistic will be deleted anyway)
     *
     * @param statistic
     */
    @Deprecated
    private void deleteOperation(Statistic statistic) {
        if (statistic.getId() == null || find(statistic.getId()) == null)
            return;


        championService.findAll().forEach(
                x -> {
                    if (x.getStatistic() != null)
                        if (x.getStatistic().getId().equals(statistic.getId())) {
                            System.out.println("YOU SHOULD NOT DELETE STATISTIC FROM CHAMPION");
                            x.setStatistic(null);
                            championService.save(x);
                        }
                }
        );
        monsterService.findAll().forEach(
                x -> {
                    if (x.getStatistic() != null)
                        if (x.getStatistic().getId().equals(statistic.getId())) {
                            System.out.println("YOU SHOULD NOT DELETE STATISTIC FROM MONSTER");
                            x.setStatistic(null);
                            monsterService.save(x);
                        }
                }
        );
        itemService.findAll().forEach(
                x -> {
                    if (x.getStatistic() != null)
                        if (x.getStatistic().getId().equals(statistic.getId())) {
                            System.out.println("YOU SHOULD NOT DELETE STATISTIC FROM ITEM");
                            x.setStatistic(null);
                            itemService.save(x);
                        }
                }
        );

        repository.deleteById(statistic.getId());
    }
}
