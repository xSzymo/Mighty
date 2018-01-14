package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Division;
import game.mightywarriors.other.enums.League;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface DivisionRepository extends CrudRepository<Division, Long> {
    HashSet<Division> findAll();

    Division findById(long id);

    Division findByLeague(League league);

    void deleteById(long id);
}