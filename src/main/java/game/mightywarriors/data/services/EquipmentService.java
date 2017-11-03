package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.EquipmentRepository;
import game.mightywarriors.data.repositories.ItemRepository;
import game.mightywarriors.data.repositories.StatisticRepository;
import game.mightywarriors.data.tables.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedList;

@Service
@Transactional
public class EquipmentService {
    @Autowired
    private EquipmentRepository repository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private StatisticRepository statisticRepository;

    public void save(Equipment equipment) {
        if (equipment != null) {
            statisticRepository.save(equipment.getArmor().getStatistic());
            itemRepository.save(equipment.getArmor());
            statisticRepository.save(equipment.getBoots().getStatistic());
            itemRepository.save(equipment.getBoots());
            statisticRepository.save(equipment.getBracelet().getStatistic());
            itemRepository.save(equipment.getBracelet());
            statisticRepository.save(equipment.getGloves().getStatistic());
            itemRepository.save(equipment.getGloves());
            statisticRepository.save(equipment.getHelmet().getStatistic());
            itemRepository.save(equipment.getHelmet());
            statisticRepository.save(equipment.getLegs().getStatistic());
            itemRepository.save(equipment.getLegs());
            statisticRepository.save(equipment.getNecklace().getStatistic());
            itemRepository.save(equipment.getNecklace());
            statisticRepository.save(equipment.getOffhand().getStatistic());
            itemRepository.save(equipment.getOffhand());
            statisticRepository.save(equipment.getRing().getStatistic());
            itemRepository.save(equipment.getRing());
            statisticRepository.save(equipment.getWeapon().getStatistic());
            itemRepository.save(equipment.getWeapon());
            repository.save(equipment);
        }
    }

    public void save(Collection<Equipment> equipments) {
        equipments.forEach(
                x -> {
                    if (x != null)
                        save(x);
                });
    }

    public Equipment findOne(long id) {
        try {
            return repository.findById(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Equipment findOne(Equipment equipment) {
        try {
            return findOne(equipment.getId());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public LinkedList<Equipment> findAll() {
        return repository.findAll();
    }

    public void delete(long id) {
        deleteOperation(findOne(id));
    }

    public void delete(Equipment equipment) {
        deleteOperation(equipment);
    }

    public void delete(Collection<Equipment> equipments) {
        equipments.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }

    private void deleteOperation(Equipment equipment) {
        try {
            itemRepository.delete(equipment.getArmor());
            itemRepository.delete(equipment.getBoots());
            itemRepository.delete(equipment.getBracelet());
            itemRepository.delete(equipment.getGloves());
            itemRepository.delete(equipment.getHelmet());
            itemRepository.delete(equipment.getLegs());
            itemRepository.delete(equipment.getNecklace());
            itemRepository.delete(equipment.getOffhand());
            itemRepository.delete(equipment.getRing());
            itemRepository.delete(equipment.getWeapon());
            repository.delete(equipment);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
