package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Ranking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface RankingRepository extends CrudRepository<Ranking, String> {
    Ranking findByNickname(String nickname);

    @Query("SELECT MAX(ranking) FROM Ranking")
    long getMaxRanking();

    LinkedList<Ranking> findAll();
}