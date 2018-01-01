package game.mightywarriors.web.rest.api;


import game.mightywarriors.data.repositories.ShopRepository;
import game.mightywarriors.data.tables.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.Set;

@RestController
public class ShopApiController {
    @Autowired
    ShopRepository shopRepository;

    @GetMapping("shops")
    public Set<Shop> getShops() {
        return shopRepository.findAll();
    }

    @GetMapping("shops/{id}")
    public Shop getShop(@PathVariable("id") String id) {
        return shopRepository.findById(Long.parseLong(id));
    }
}
