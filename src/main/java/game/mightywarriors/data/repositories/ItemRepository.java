package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Statistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
}