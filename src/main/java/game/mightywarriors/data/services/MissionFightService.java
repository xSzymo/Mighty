package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.MissionFightRepository;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.MissionFight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class MissionFightService {
    @Autowired
    private MissionFightRepository repository;

    public void save(MissionFight missionFight) {
        if (missionFight != null)
            saveOperation(missionFight);
    }

    public void save(Set<MissionFight> missionFights) {
        missionFights.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(MissionFight missionFight) {
        if (missionFight.getChampion() == null || missionFight.getMission() == null || missionFight.getBlockDate() == null)
            return;

        repository.save(missionFight);
    }

    public MissionFight findOne(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public MissionFight findOne(MissionFight missionFight) {
        try {
            return findOne(missionFight.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public MissionFight findLatestByChampionId(Champion champion) {
        try {
            HashSet<MissionFight> missionFights = repository.findByChampionId(champion.getId());
            MissionFight missionFight = missionFights.iterator().next();

            for (MissionFight fight : missionFights)
                if (missionFight.getBlockDate().before(fight.getBlockDate()))
                    missionFight = fight;

            return missionFight;
        } catch (Exception e) {
            return null;
        }
    }

    public MissionFight findLatestByChampionId(long id) {
        try {
            HashSet<MissionFight> missionFights = repository.findByChampionId(id);
            MissionFight missionFight = missionFights.iterator().next();

            for (MissionFight fight : missionFights)
                if (missionFight.getBlockDate().before(fight.getBlockDate()))
                    missionFight = fight;

            return missionFight;
        } catch (Exception e) {
            return null;
        }
    }

    public HashSet<MissionFight> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(findOne(id));
    }

    public void delete(MissionFight image) {
        try {
            deleteOperation(image);
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<MissionFight> missionFights) {
        missionFights.forEach(
                x -> {
                    if (x != null)
                        deleteOperation(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(MissionFight missionFight) {
        if (missionFight.getId() == null || findOne(missionFight.getId()) == null)
            return;

        missionFight.setChampion(null);
        missionFight.setMission(null);
        repository.deleteById(missionFight.getId());
    }
}
