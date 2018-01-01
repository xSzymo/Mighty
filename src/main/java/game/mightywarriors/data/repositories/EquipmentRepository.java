package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {
    HashSet<Equipment> findAll();

    Equipment findById(long id);

    void deleteById(long id);
}