package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.ChampionRepository;
import game.mightywarriors.data.repositories.MonsterRepository;
import game.mightywarriors.data.repositories.StatisticRepository;
import game.mightywarriors.data.tables.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedList;

@Service
@Transactional
public class StatisticService {
    @Autowired
    private StatisticRepository repository;
    @Autowired
    private ChampionRepository championRepository;
    @Autowired
    private MonsterRepository monsterRepository;
    @Autowired
    private ItemService itemService;

    public void save(Statistic image) {
        if (image != null)
            repository.save(image);
    }

    public void save(LinkedList<Statistic> images) {
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

    public LinkedList<Statistic> findAll() {
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

    private void deleteOperation(Statistic statistic) {
        if (statistic.getId() == null || findOne(statistic.getId()) == null)
            return;

        championRepository.findAll().forEach(
                x -> {
                    if (x.getStatistic() != null)
                        if (x.getStatistic().getId().equals(statistic.getId())) {
                            x.setStatistic(null);
                            championRepository.save(x);
                        }
                }
        );
        monsterRepository.findAll().forEach(
                x -> {
                    if (x.getStatistic() != null)
                        if (x.getStatistic().getId().equals(statistic.getId())) {
                            x.setStatistic(null);
                            monsterRepository.save(x);
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

        repository.delete(statistic);
    }
}
