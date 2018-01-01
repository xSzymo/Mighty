package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Monster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface MonsterRepository extends CrudRepository<Monster, Long> {
    Monster findById(long id);

    HashSet<Monster> findAll();

    void deleteById(long id);
}