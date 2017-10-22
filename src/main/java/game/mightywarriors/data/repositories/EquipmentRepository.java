package game.mightywarriors.data.repositories;

import game.mightywarriors.data.tables.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {
}