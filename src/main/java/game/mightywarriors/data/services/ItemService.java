package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.ItemRepository;
import game.mightywarriors.data.tables.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ItemService {
    @Autowired
    private ItemRepository repository;

    public void save(Item image) {
        if (image != null)
            repository.save(image);
    }

    public void save(Collection<Item> images) {
        images.forEach(
                x -> {
                    if (x != null)
                        repository.save(x);
                });
    }

    public Item findOne(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Item findOne(Item items) {
        try {
            return findOne(items.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Iterable<Item> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void delete(Item item) {
        try {
            delete(item.getId());
        } catch (NullPointerException e) {

        }
    }

    public void delete(Collection<Item> items) {
        items.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }

    private void deleteOperation(Item item) {
       // item.getImage()
    }
}
