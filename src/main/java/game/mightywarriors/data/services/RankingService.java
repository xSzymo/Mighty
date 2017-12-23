package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.RankingRepository;
import game.mightywarriors.data.tables.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RankingService {
    @Autowired
    private RankingRepository repository;

    public void save(Ranking ranking) {
        if (ranking != null)
            saveOperation(ranking);
    }

    public void save(Collection<Ranking> rankings) {
        rankings.forEach(
                x -> {
                    if (x != null)
                        saveOperation(x);
                });
    }

    private void saveOperation(Ranking ranking) {
        if (findOne(ranking.getNickname()) == null)
            ranking.setRanking(getMaxRanking() + 1);

        repository.save(ranking);
    }

    public Ranking findOne(String nickname) {
        try {
            return repository.findByNickname(nickname);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Ranking findOne(Ranking ranking) {
        try {
            return findOne(ranking.getNickname());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public long getMaxRanking() {
        try {
            return repository.getMaxRanking();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Ranking> findAllBelowRanking(long ranking) {
        try {
            return findAll().stream().filter(x -> x.getRanking() >= ranking).collect(Collectors.toList());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LinkedList<Ranking> findAll() {
        return repository.findAll();
    }

    public void delete(String nickname) {
        if (repository.findByNickname(nickname) != null)
            repository.deleteById(nickname);
    }

    public void delete(Ranking ranking) {
        try {
            delete(ranking.getNickname());
        } catch (NullPointerException e) {

        }
    }

    public void deleteAll() {
        delete(findAll());
    }

    public void delete(Collection<Ranking> rankings) {
        rankings.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }
}
