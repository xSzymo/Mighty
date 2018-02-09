package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.DungeonFight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface DungeonFightRepository extends CrudRepository<DungeonFight, Long> {
    HashSet<DungeonFight> findAll();

    DungeonFight findById(long id);

    DungeonFight findByUserId(long id);

    void deleteById(long id);
}