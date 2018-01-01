package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.MissionFight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface MissionFightRepository extends CrudRepository<MissionFight, Long> {
    HashSet<MissionFight> findAll();

    MissionFight findById(long id);

    HashSet<MissionFight> findByChampionId(long id);

    void deleteById(long id);
}