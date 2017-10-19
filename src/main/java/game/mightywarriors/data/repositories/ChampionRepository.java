package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Statistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionRepository extends CrudRepository<Champion, Long> {
}