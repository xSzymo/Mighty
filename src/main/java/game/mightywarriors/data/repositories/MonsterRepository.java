package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Monster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface MonsterRepository extends CrudRepository<Monster, Long> {
    Monster findById(long id);

    HashSet<Monster> findAll();

    void deleteById(long id);
}