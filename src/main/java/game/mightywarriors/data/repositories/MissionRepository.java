package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Mission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface MissionRepository extends CrudRepository<Mission, Long> {
    Mission findById(long id);

    HashSet<Mission> findAll();

    void deleteById(long id);
}