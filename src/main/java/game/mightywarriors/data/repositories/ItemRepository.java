package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    HashSet<Item> findAll();

    Item findById(long id);

    void deleteById(long id);
}