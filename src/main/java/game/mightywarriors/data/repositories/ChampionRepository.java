package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Champion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface ChampionRepository extends CrudRepository<Champion, Long> {
    LinkedList<Champion> findAll();

    Champion findById(long id);

    void deleteById(long id);
}