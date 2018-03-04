package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.InventoryItemRepository;
import game.mightywarriors.data.tables.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class InventoryItemService {
    @Autowired
    private InventoryItemRepository repository;

    public void save(InventoryItem item) {
        if (item != null)
            saveOperation(item);
    }

    public void save(Collection<InventoryItem> items) {
        items.forEach(this::save);
    }

    private void saveOperation(InventoryItem item) {
        repository.save(item);
    }

    public InventoryItem find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public InventoryItem find(InventoryItem items) {
        try {
            return find(items.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Set<InventoryItem> findByItemId(long id) {
        try {
            return repository.findByItemId(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<InventoryItem> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        InventoryItem one = find(id);
        if (one != null)
            deleteOperation(one);
    }

    public void delete(InventoryItem item) {
        if (item != null)
            if (item.getId() != null)
                delete(item.getId());
    }

    public void delete(Collection<InventoryItem> items) {
        items.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(InventoryItem item) {
        if (item.getId() != null) {
            repository.deleteById(item.getId());
        }
    }
}
