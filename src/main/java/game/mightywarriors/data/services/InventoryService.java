package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.InventoryRepository;
import game.mightywarriors.data.tables.Inventory;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepository repository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    public void save(Inventory inventory) {
        if (inventory != null)
            saveOperation(inventory);
    }

    public void save(Set<Inventory> inventories) {
        inventories.forEach(
                x -> save(x));
    }

    private void saveOperation(Inventory inventory) {
        inventory.getItems().forEach(x -> {
            try {
                itemService.save(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        try {
            repository.save(inventory);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Inventory findOne(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Inventory findOne(Inventory inventory) {
        try {
            return findOne(inventory.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Inventory> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        Inventory one = findOne(id);
        if (one != null)
            deleteOperation(one);
    }

    public void delete(Inventory inventory) {
        try {
            if (inventory.getId() != null)
                delete(inventory.getId());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void delete(Collection<Inventory> inventories) {
        inventories.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Inventory inventory) {
        User userInventory = userService.findByInventory(inventory);

        if (userInventory != null)
            if (userInventory.getInventory() != null) {
                if (userInventory.getInventory().getId().equals(inventory.getId())) {
                    userInventory.setInventory(null);
                    userService.save(userInventory);
                }
            }

        repository.deleteById(inventory.getId());
    }
}
