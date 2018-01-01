package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.ItemRepository;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.other.exceptions.WrongTypeItemException;
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
    private ShopService shopService;
    @Autowired
    private EquipmentService equipmentService;

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
        if (item.getImage() != null)
            try {
                imageService.save(item.getImage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        repository.save(item);
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

    public HashSet<Item> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        Item one = findOne(id);
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

        HashSet<Item> items = new HashSet<>();
        shopService.findAll().forEach(
                x -> {
                    x.getItems().forEach(x1 ->
                            items.add(x1)
                    );
//                    items.forEach(x1 -> x.getItems().remove(x1));
                    items.forEach(x.getItems()::remove);
//                    x.getItems().remove(items);
                    shopService.save(x);
                    items.clear();
                }
        );


        if (item.getStatistic() != null)
            statisticService.delete(item.getStatistic());

        equipmentService.findAll().forEach(
                x -> {
                    try {
                        if (x.getId() != null) {
                            if (x.getArmor() != null)
                                if (x.getArmor().getId().equals(item.getId())) {
                                    x.setArmor(null);
                                    equipmentService.save(x);
                                }
                            if (x.getWeapon() != null)
                                if (x.getWeapon().getId().equals(item.getId())) {
                                    x.setWeapon(null);
                                    equipmentService.save(x);
                                }
                            if (x.getBoots() != null)
                                if (x.getBoots().getId().equals(item.getId())) {
                                    x.setBoots(null);
                                    equipmentService.save(x);
                                }
                            if (x.getBracelet() != null)
                                if (x.getBracelet().getId().equals(item.getId())) {
                                    x.setBracelet(null);
                                    equipmentService.save(x);
                                }
                            if (x.getGloves() != null)
                                if (x.getGloves().getId().equals(item.getId())) {
                                    x.setGloves(null);
                                    equipmentService.save(x);
                                }
                            if (x.getHelmet() != null)
                                if (x.getHelmet().getId().equals(item.getId())) {
                                    x.setHelmet(null);
                                    equipmentService.save(x);
                                }
                            if (x.getLegs() != null)
                                if (x.getLegs().getId().equals(item.getId())) {
                                    x.setLegs(null);
                                    equipmentService.save(x);
                                }
                            if (x.getNecklace() != null)
                                if (x.getNecklace().getId().equals(item.getId())) {
                                    x.setNecklace(null);
                                    equipmentService.save(x);
                                }
                            if (x.getOffhand() != null)
                                if (x.getOffhand().getId().equals(item.getId())) {
                                    x.setOffhand(null);
                                    equipmentService.save(x);
                                }
                            if (x.getRing() != null)
                                if (x.getRing().getId().equals(item.getId())) {
                                    x.setRing(null);
                                    equipmentService.save(x);
                                }
                        }
                    } catch (WrongTypeItemException e) {
                        e.printStackTrace();
                    }
                }
        );

        repository.deleteById(item.getId());
    }
}
