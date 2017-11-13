package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.repositories.ItemRepository;
import game.mightywarriors.data.tables.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class ItemsApiController {
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("api/items")
    public LinkedList<Item> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("api/items/{id}")
    public Item getItem(@PathVariable("id") String id) {
        return itemRepository.findById(Long.parseLong(id));
    }
}
