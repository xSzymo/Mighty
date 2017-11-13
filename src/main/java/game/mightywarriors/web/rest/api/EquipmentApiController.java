package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.repositories.EquipmentRepository;
import game.mightywarriors.data.tables.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class EquipmentApiController {
    @Autowired
    EquipmentRepository equipmentRepository;

    @GetMapping("api/equipments")
    public LinkedList<Equipment> getEquipments() {
        return equipmentRepository.findAll();
    }

    @GetMapping("api/equipments/{id}")
    public Equipment getEquipment(@PathVariable("id") String id) {
        return equipmentRepository.findById(Long.parseLong(id));
    }
}
