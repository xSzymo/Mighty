package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Mission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface MissionRepository extends CrudRepository<Mission, Long> {
    Mission findById(long id);

    LinkedList<Mission> findAll();

    void deleteById(long id);
}