package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.MissionFight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface MissionFightRepository extends CrudRepository<MissionFight, Long> {
    LinkedList<MissionFight> findAll();

    MissionFight findById(long id);

    LinkedList<MissionFight> findByChampionId(long id);

    void deleteById(long id);
}