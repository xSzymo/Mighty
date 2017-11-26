package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Division;
import game.mightywarriors.other.enums.League;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface DivisionRepository extends CrudRepository<Division, Long> {
    LinkedList<Division> findAll();

    Division findById(long id);

    Division findByLeague(League league);

    void deleteById(long id);
}