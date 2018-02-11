package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface RequestRepository extends CrudRepository<Request, Long> {
    Request findById(long id);

    HashSet<Request> findAll();

    void deleteById(Long id);
}