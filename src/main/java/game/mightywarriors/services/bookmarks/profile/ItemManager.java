package game.mightywarriors.services.bookmarks.profile;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemManager {
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever usersRetriever;

    public void moveEquipmentItemToInventory(String authorization, long itemId) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        for (Champion champion : user.getChampions()) {
            if (champion.getEquipment().getNecklace() != null)
                if (champion.getEquipment().getNecklace().getId().equals(itemId)) {
                    user.getInventory().addItem(champion.getEquipment().getNecklace());
                    champion.getEquipment().setNecklace(null);

                    userService.save(user);
                }
            if (champion.getEquipment().getBracelet() != null)
                if (champion.getEquipment().getBracelet().getId().equals(itemId)) {
                    user.getInventory().addItem(champion.getEquipment().getBracelet());
                    champion.getEquipment().setBracelet(null);

                    userService.save(user);
                }
            if (champion.getEquipment().getRing() != null)
                if (champion.getEquipment().getRing().getId().equals(itemId)) {
                    user.getInventory().addItem(champion.getEquipment().getRing());
                    champion.getEquipment().setRing(null);

                    userService.save(user);
                }
            if (champion.getEquipment().getBoots() != null)
                if (champion.getEquipment().getBoots().getId().equals(itemId)) {
                    user.getInventory().addItem(champion.getEquipment().getBoots());
                    champion.getEquipment().setBoots(null);

                    userService.save(user);
                }
            if (champion.getEquipment().getLegs() != null)
                if (champion.getEquipment().getLegs().getId().equals(itemId)) {
                    user.getInventory().addItem(champion.getEquipment().getLegs());
                    champion.getEquipment().setLegs(null);

                    userService.save(user);
                }
            if (champion.getEquipment().getGloves() != null)
                if (champion.getEquipment().getGloves().getId().equals(itemId)) {
                    user.getInventory().addItem(champion.getEquipment().getGloves());
                    champion.getEquipment().setGloves(null);

                    userService.save(user);
                }
            if (champion.getEquipment().getHelmet() != null)
                if (champion.getEquipment().getHelmet().getId().equals(itemId)) {
                    user.getInventory().addItem(champion.getEquipment().getHelmet());
                    champion.getEquipment().setHelmet(null);

                    userService.save(user);
                }
            if (champion.getEquipment().getOffhand() != null)
                if (champion.getEquipment().getOffhand().getId().equals(itemId)) {
                    user.getInventory().addItem(champion.getEquipment().getOffhand());
                    champion.getEquipment().setOffhand(null);

                    userService.save(user);
                }
            if (champion.getEquipment().getWeapon() != null)
                if (champion.getEquipment().getWeapon().getId().equals(itemId)) {
                    user.getInventory().addItem(champion.getEquipment().getWeapon());
                    champion.getEquipment().setWeapon(null);

                    userService.save(user);
                }
            if (champion.getEquipment().getArmor() != null)
                if (champion.getEquipment().getArmor().getId().equals(itemId)) {
                    user.getInventory().addItem(champion.getEquipment().getArmor());
                    champion.getEquipment().setArmor(null);

                    userService.save(user);
                }
        }
    }

    public void moveInventoryToEquipmentItem(String authorization, long itemId, long championId) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Item item = user.getInventory().getItems().stream().filter(x -> x.getId().equals(itemId)).findFirst().get();

        if (item != null) {
            for (Champion champion : user.getChampions()) {
                if (champion.getId().equals(championId)) {
                    if (item.getWeaponType().getType().equals(WeaponType.WEAPON.getType())) {
                        champion.getEquipment().setWeapon(item);
                        user.getInventory().getItems().remove(item);
                    } else if (item.getWeaponType().getType().equals(WeaponType.OFFHAND.getType())) {
                        champion.getEquipment().setOffhand(item);
                        user.getInventory().getItems().remove(item);
                    } else if (item.getWeaponType().getType().equals(WeaponType.HELMET.getType())) {
                        champion.getEquipment().setHelmet(item);
                        user.getInventory().getItems().remove(item);
                    } else if (item.getWeaponType().getType().equals(WeaponType.ARMOR.getType())) {
                        champion.getEquipment().setArmor(item);
                        user.getInventory().getItems().remove(item);
                    } else if (item.getWeaponType().getType().equals(WeaponType.GLOVES.getType())) {
                        champion.getEquipment().setGloves(item);
                        user.getInventory().getItems().remove(item);
                    } else if (item.getWeaponType().getType().equals(WeaponType.LEGS.getType())) {
                        champion.getEquipment().setLegs(item);
                        user.getInventory().getItems().remove(item);
                    } else if (item.getWeaponType().getType().equals(WeaponType.BOOTS.getType())) {
                        champion.getEquipment().setBoots(item);
                        user.getInventory().getItems().remove(item);
                    } else if (item.getWeaponType().getType().equals(WeaponType.RING.getType())) {
                        champion.getEquipment().setRing(item);
                        user.getInventory().getItems().remove(item);
                    } else if (item.getWeaponType().getType().equals(WeaponType.NECKLACE.getType())) {
                        champion.getEquipment().setNecklace(item);
                        user.getInventory().getItems().remove(item);
                    } else if (item.getWeaponType().getType().equals(WeaponType.BRACELET.getType())) {
                        champion.getEquipment().setBracelet(item);
                        user.getInventory().getItems().remove(item);
                    }
                }
            }
            userService.save(user);
        }
    }
}
