package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    LinkedList<Item> findAll();

    Item findById(long id);

    void deleteById(long id);
}