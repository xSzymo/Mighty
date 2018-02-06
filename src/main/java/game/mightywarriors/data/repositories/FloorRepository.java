package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Floor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface FloorRepository extends CrudRepository<Floor, Long> {
    Floor findById(long id);

    HashSet<Floor> findAll();

    void deleteById(long id);
}
