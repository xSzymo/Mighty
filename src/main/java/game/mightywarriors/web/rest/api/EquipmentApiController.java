package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.repositories.EquipmentRepository;
import game.mightywarriors.data.tables.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class EquipmentApiController {
    @Autowired
    EquipmentRepository equipmentRepository;

    @GetMapping("equipments")
    public Set<Equipment> getEquipments() {
        return equipmentRepository.findAll();
    }

    @GetMapping("equipments/{id}")
    public Equipment getEquipment(@PathVariable("id") String id) {
        return equipmentRepository.findById(Long.parseLong(id));
    }
}
