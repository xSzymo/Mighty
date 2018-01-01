package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.repositories.ItemRepository;
import game.mightywarriors.data.tables.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Service;
import java.util.LinkedList;
import java.util.Set;

@RestController
public class ItemsApiController {
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("items")
    public Set<Item> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("items/{id}")
    public Item getItem(@PathVariable("id") String id) {
        return itemRepository.findById(Long.parseLong(id));
    }
}
