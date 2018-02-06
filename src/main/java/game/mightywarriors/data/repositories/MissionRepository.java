package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Mission;
import game.mightywarriors.data.tables.Monster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface MissionRepository extends CrudRepository<Mission, Long> {
    Mission findById(long id);

    Mission findByMonster(Monster monster);

    HashSet<Mission> findAll();

    void deleteById(long id);
}
