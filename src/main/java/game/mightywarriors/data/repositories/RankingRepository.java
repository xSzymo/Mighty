package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Ranking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface RankingRepository extends CrudRepository<Ranking, String> {
    Ranking findByNickname(String nickname);

    Ranking findByRanking(long ranking);

    @Query("SELECT MAX(ranking) FROM Ranking")
    long getMaxRanking();

    @Query(value = "SELECT r FROM Ranking r WHERE r.ranking > :ranking")
    List<Ranking> findAllAbove(@Param("ranking") long ranking);

    @Query(value = "SELECT r FROM Ranking r WHERE r.ranking >= :ranking")
    List<Ranking> findAllAboveAndEqual(@Param("ranking") long ranking);

    @Query(value = "SELECT r FROM Ranking r WHERE r.ranking < :ranking")
    List<Ranking> findAllBelow(@Param("ranking") long ranking);

    @Query(value = "SELECT r FROM Ranking r WHERE r.ranking <= :ranking")
    List<Ranking> findAllBelowAndEqual(@Param("ranking") long ranking);

    @Query(value = "SELECT r FROM Ranking r WHERE r.ranking < :low AND r.ranking >= :high")
    List<Ranking> findAllBetween(@Param("low") long low, @Param("high") long high);

    LinkedList<Ranking> findAll();
}