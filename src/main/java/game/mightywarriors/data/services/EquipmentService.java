package game.mightywarriors.data.services;

import game.mightywarriors.data.repositories.EquipmentRepository;
import game.mightywarriors.data.tables.Equipment;
import game.mightywarriors.other.exceptions.WrongTypeItemException;
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
    private ItemService itemService;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private ChampionService championService;

    public void save(Equipment equipment) {
        if (equipment != null) {
            if (equipment.getArmor() != null) {
                statisticService.save(equipment.getArmor().getStatistic());
                itemService.save(equipment.getArmor());
            }
            if (equipment.getBoots() != null) {
                statisticService.save(equipment.getBoots().getStatistic());
                itemService.save(equipment.getBoots());
            }
            if (equipment.getBracelet() != null) {
                statisticService.save(equipment.getBracelet().getStatistic());
                itemService.save(equipment.getBracelet());
            }
            if (equipment.getGloves() != null) {
                statisticService.save(equipment.getGloves().getStatistic());
                itemService.save(equipment.getGloves());
            }
            if (equipment.getHelmet() != null) {
                statisticService.save(equipment.getHelmet().getStatistic());
                itemService.save(equipment.getHelmet());
            }
            if (equipment.getLegs() != null) {
                statisticService.save(equipment.getLegs().getStatistic());
                itemService.save(equipment.getLegs());
            }
            if (equipment.getNecklace() != null) {
                statisticService.save(equipment.getNecklace().getStatistic());
                itemService.save(equipment.getNecklace());
            }
            if (equipment.getOffhand() != null) {
                statisticService.save(equipment.getOffhand().getStatistic());
                itemService.save(equipment.getOffhand());
            }
            if (equipment.getRing() != null) {
                statisticService.save(equipment.getRing().getStatistic());
                itemService.save(equipment.getRing());
            }
            if (equipment.getWeapon() != null) {
                statisticService.save(equipment.getWeapon().getStatistic());
                itemService.save(equipment.getWeapon());
            }
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
        Equipment equipment = findOne(id);
        if (equipment != null)
            deleteOperation(equipment);
    }

    public void delete(Equipment equipment) {
        if (equipment.getId() != null)
            delete(equipment.getId());
    }

    public void delete(Collection<Equipment> equipments) {
        equipments.forEach(
                x -> {
                    if (x != null)
                        delete(x);
                });
    }

    public void deleteAll() {
        delete(findAll());
    }

    private void deleteOperation(Equipment equipment) {
        try {
            championService.findAll().forEach(
                    x -> {
                        if (x.getEquipment() != null)
                            if (x.getEquipment().getId().equals(equipment.getId())) {
                                x.setEquipment(null);
                                championService.save(x);
                            }
                    }
            );
            equipment.setArmor(null);
            equipment.setBoots(null);
            equipment.setBracelet(null);
            equipment.setGloves(null);
            equipment.setHelmet(null);
            equipment.setLegs(null);
            equipment.setNecklace(null);
            equipment.setOffhand(null);
            equipment.setRing(null);
            equipment.setWeapon(null);

            repository.deleteById(equipment.getId());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (WrongTypeItemException e) {
            e.printStackTrace();
        }
    }
}
