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

    public void save(Statistic image) {
        if (image != null)
            repository.save(image);
    }

    public void save(Set<Statistic> images) {
        images.forEach(
                x -> {
                    if (x != null)
                        repository.save(x);
                });
    }

    public Statistic findOne(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Statistic findOne(Statistic image) {
        try {
            return findOne(image.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Statistic> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(findOne(id));
    }

    public void delete(Statistic image) {
        try {
            deleteOperation(image);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Statistic> addresses) {
        addresses.forEach(
                x -> {
                    if (x != null)
                        deleteOperation(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Statistic statistic) {
        if (statistic.getId() == null || findOne(statistic.getId()) == null)
            return;

        championService.findAll().forEach(
                x -> {
                    if (x.getStatistic() != null)
                        if (x.getStatistic().getId().equals(statistic.getId())) {
                            x.setStatistic(null);
                            championService.save(x);
                        }
                }
        );
        monsterService.findAll().forEach(
                x -> {
                    if (x.getStatistic() != null)
                        if (x.getStatistic().getId().equals(statistic.getId())) {
                            x.setStatistic(null);
                            monsterService.save(x);
                        }
                }
        );
        itemService.findAll().forEach(
                x -> {
                    if (x.getStatistic() != null)
                        if (x.getStatistic().getId().equals(statistic.getId())) {
                            x.setStatistic(null);
                            itemService.save(x);
                        }
                }
        );

        repository.deleteById(statistic.getId());
    }
}
