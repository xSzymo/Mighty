package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.InventoryItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Repository
@Transactional
public interface InventoryItemRepository extends CrudRepository<InventoryItem, Long> {
    HashSet<InventoryItem> findAll();

    InventoryItem findById(long id);

    Set<InventoryItem> findByItemId(long id);

    void deleteById(long id);
}
