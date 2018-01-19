package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.services.InventoryService;
import game.mightywarriors.data.tables.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
public class InventoryApiController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("inventories")
    public HashSet<Inventory> getShops() {
        return inventoryService.findAll();
    }

    @GetMapping("inventories/{id}")
    public Inventory getShop(@PathVariable("id") String id) {
        return inventoryService.find(Long.parseLong(id));
    }
}
