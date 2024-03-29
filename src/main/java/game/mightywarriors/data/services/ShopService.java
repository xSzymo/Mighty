package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.ShopRepository;
import game.mightywarriors.data.tables.Shop;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class ShopService {
    @Autowired
    private ShopRepository repository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    public void save(Shop shop) {
        if (shop != null)
            saveOperation(shop);
    }

    public void save(Set<Shop> shops) {
        shops.forEach(
                x -> save(x));
    }

    private void saveOperation(Shop shop) {
        shop.getItems().forEach(x -> {
            try {
                itemService.save(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        try {
            repository.save(shop);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Shop find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Shop find(Shop shop) {
        try {
            return find(shop.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Shop> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        Shop one = find(id);
        if (one != null)
            deleteOperation(one);
    }

    public void delete(Shop shop) {
        try {
            if (shop.getId() != null)
                delete(shop.getId());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void delete(Collection<Shop> shops) {
        shops.forEach(this::delete);
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Shop shop) {
        User userShop = userService.find(shop);
        if (userShop != null)
            if (userShop.getShop() != null)
                if (userShop.getShop().getId().equals(shop.getId())) {
                    userShop.setShop(null);
                    userService.save(userShop);
                }

        repository.deleteById(shop.getId());
    }
}
