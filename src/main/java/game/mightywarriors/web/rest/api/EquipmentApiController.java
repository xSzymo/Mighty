package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.services.EquipmentService;
import game.mightywarriors.data.tables.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class EquipmentApiController {
    @Autowired
    private EquipmentService service;

    @GetMapping("equipments")
    public Set<Equipment> getEquipments() {
        return service.findAll();
    }

    @GetMapping("equipments/{id}")
    public Equipment getEquipment(@PathVariable("id") String id) {
        return service.find(Long.parseLong(id));
    }
}
