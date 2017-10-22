package game.mightywarriors.web.rest;


import game.mightywarriors.data.repositories.ItemRepository;
import game.mightywarriors.data.tables.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class Helo1 {
    @Autowired
    ItemRepository userRepository;

    @GetMapping("items")
    public LinkedList<Item> halo() {
        return userRepository.findAll();
    }

    @GetMapping("items/{id}")
    public Item halo1(@PathVariable("id") String id) {
        return userRepository.findById(Long.parseLong(id));
    }


}