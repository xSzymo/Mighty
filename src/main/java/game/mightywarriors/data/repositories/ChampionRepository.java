package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Champion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface ChampionRepository extends CrudRepository<Champion, Long> {
    HashSet<Champion> findAll();

    Champion findById(long id);

    Champion findByStatisticId(long id);

    void deleteById(long id);
}