package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Ranking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public interface RankingRepository extends CrudRepository<Ranking, String> {
    Ranking findByNickname(String nickname);

    Ranking findByRanking(long ranking);

    @Query("SELECT MAX(ranking) FROM Ranking")
    long getMaxRanking();

    @Query(value = "SELECT r FROM Ranking r WHERE r.ranking > :ranking")
    Set<Ranking> findAllAbove(@Param("ranking") long ranking);

    @Query(value = "SELECT r FROM Ranking r WHERE r.ranking >= :ranking")
    Set<Ranking> findAllAboveAndEqual(@Param("ranking") long ranking);

    @Query(value = "SELECT r FROM Ranking r WHERE r.ranking < :ranking")
    Set<Ranking> findAllBelow(@Param("ranking") long ranking);

    @Query(value = "SELECT r FROM Ranking r WHERE r.ranking <= :ranking")
    Set<Ranking> findAllBelowAndEqual(@Param("ranking") long ranking);

    @Query(value = "SELECT r FROM Ranking r WHERE r.ranking < :low AND r.ranking >= :high")
    Set<Ranking> findAllBetween(@Param("low") long low, @Param("high") long high);

    HashSet<Ranking> findAll();
}