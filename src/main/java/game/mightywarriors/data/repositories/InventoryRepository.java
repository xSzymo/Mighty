package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    HashSet<Inventory> findAll();

    Inventory findById(long id);

    void deleteById(long id);
}