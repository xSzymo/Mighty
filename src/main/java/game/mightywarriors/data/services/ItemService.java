package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.ItemRepository;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class ItemService {
    @Autowired
    private ItemRepository repository;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private InventoryItemService inventoryItemService;

    public void save(Item item) {
        if (item != null)
            saveOperation(item);
    }

    public void save(Collection<Item> images) {
        images.forEach(this::save);
    }

    private void saveOperation(Item item) {
        if (item.getStatistic() != null)
            statisticService.save(item.getStatistic());
        else {
            Statistic statistic = new Statistic();
            item.setStatistic(statistic);
            statisticService.save(statistic);
        }
        if (item.getImage() != null)
            try {
                imageService.save(item.getImage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        repository.save(item);
    }

    public Item find(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Item find(Item items) {
        try {
            return find(items.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public HashSet<Item> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        Item one = find(id);
        if (one != null)
            deleteOperation(one);
    }

    public void delete(Item item) {
        if (item != null)
            if (item.getId() != null)
                delete(item.getId());
    }

    public void delete(Collection<Item> items) {
        items.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Item item) {
        if (item.getId() != null) {
            inventoryItemService.delete(inventoryItemService.findByItemId(item.getId()));
            repository.deleteById(item.getId());
        }
    }
}
