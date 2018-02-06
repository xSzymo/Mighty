package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Dungeon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface DungeonRepository extends CrudRepository<Dungeon, Long> {
    Dungeon findById(long id);

    HashSet<Dungeon> findAll();

    void deleteById(long id);
}
