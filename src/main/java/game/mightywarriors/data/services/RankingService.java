package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.RankingRepository;
import game.mightywarriors.data.tables.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
        rankings.stream().filter(x -> x != null).forEach(this::saveOperation);
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

    public Ranking findOne(long ranking) {
        try {
            return repository.findByRanking(ranking);
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

    public Set<Ranking> findAllAboveRanking(long ranking) {
        try {
            return repository.findAllAbove(ranking);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Set<Ranking> findAllBelowRanking(long ranking) {
        try {
            return repository.findAllBelow(ranking);
        } catch (NullPointerException e) {
            return null;
        }
    }


    public Set<Ranking> findAllAboveAndEqualRanking(long ranking) {
        try {
            return repository.findAllAboveAndEqual(ranking);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Set<Ranking> findAllBetween(long ranking) {
        try {
            return repository.findAllBelowAndEqual(ranking);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * @param low  - higher number ( lower ranking )
     * @param high - lower number  ( higher ranking )
     *             for example low - 13 & high - 3
     *             for functionality : all between low and high will be increment
     */
    public Set<Ranking> findAllBetween(long low, long high) {
        try {
            return repository.findAllBetween(low, high);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Ranking> findAll() {
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
