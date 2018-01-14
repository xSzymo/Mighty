package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;

@Repository
@Transactional
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {
    HashSet<Equipment> findAll();

    Equipment findById(long id);

    void deleteById(long id);
}