package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface ItemRepository extends CrudRepository<Item, Long> {
    HashSet<Item> findAll();

    Item findById(long id);

    void deleteById(long id);
}