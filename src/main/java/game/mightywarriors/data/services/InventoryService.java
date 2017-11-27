package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.InventoryRepository;
import game.mightywarriors.data.tables.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedList;

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

    public void save(LinkedList<Inventory> inventories) {
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

    public LinkedList<Inventory> findAll() {
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

    private void deleteOperation(Inventory inventory) {
        userService.findAll().forEach(
                x -> {
                    if (x.getInventory() != null) {
                        if (x.getInventory().getId().equals(inventory.getId())) {
                            x.setInventory(null);
                            userService.save(x);
                        }
                    }
                }
        );
        repository.deleteById(inventory.getId());
    }
}
