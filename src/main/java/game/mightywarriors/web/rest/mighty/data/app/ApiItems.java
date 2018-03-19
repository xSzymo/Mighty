package game.mightywarriors.web.rest.mighty.data.app;


import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.tables.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ApiItems {
    @Autowired
    private ItemService service;

    @GetMapping("items")
    public Set<Item> getItems() {
        return service.findAll();
    }

    @GetMapping("items/{id}")
    public Item getItem(@PathVariable("id") String id) {
        return service.find(Long.parseLong(id));
    }
}
