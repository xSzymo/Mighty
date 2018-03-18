package game.mightywarriors.services.bookmarks.profile;

import game.mightywarriors.data.services.InventoryItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.InventoryItem;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ItemType;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemManager {
    private UserService userService;
    private UsersRetriever usersRetriever;
    private InventoryItemService inventoryItemService;

    @Autowired
    public ItemManager(UserService userService, UsersRetriever usersRetriever, InventoryItemService inventoryItemService) {
        this.userService = userService;
        this.usersRetriever = usersRetriever;
        this.inventoryItemService = inventoryItemService;
    }

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
        Optional<InventoryItem> inventoryItem = user.getInventory().getItems().stream().filter(x -> x.getItem() != null && x.getItem().getId().equals(itemId)).findFirst();

        if (inventoryItem.isPresent()) {
            Item item = inventoryItem.get().getItem();
            if (inventoryItem.get().getItem() != null) {
                for (Champion champion : user.getChampions()) {
                    if (champion.getId().equals(championId)) {
                        throwExceptionIf_ChampionHaveNotEnoughLevel(champion, item);

                        if (item.getItemType().getType().equals(ItemType.WEAPON.getType())) {
                            if (champion.getEquipment().getWeapon() != null)
                                throw new Exception("Take off current item");

                            champion.getEquipment().setWeapon(item);
                            user.getInventory().getItems().remove(inventoryItem.get());

                        } else if (item.getItemType().getType().equals(ItemType.OFFHAND.getType())) {
                            if (champion.getEquipment().getOffhand() != null)
                                throw new Exception("Take off current item");

                            champion.getEquipment().setOffhand(item);
                            user.getInventory().getItems().remove(inventoryItem.get());

                        } else if (item.getItemType().getType().equals(ItemType.HELMET.getType())) {
                            if (champion.getEquipment().getHelmet() != null)
                                throw new Exception("Take off current item");

                            champion.getEquipment().setHelmet(item);
                            user.getInventory().getItems().remove(inventoryItem.get());

                        } else if (item.getItemType().getType().equals(ItemType.ARMOR.getType())) {
                            if (champion.getEquipment().getArmor() != null)
                                throw new Exception("Take off current item");

                            champion.getEquipment().setArmor(item);
                            user.getInventory().getItems().remove(inventoryItem.get());

                        } else if (item.getItemType().getType().equals(ItemType.GLOVES.getType())) {
                            if (champion.getEquipment().getGloves() != null)
                                throw new Exception("Take off current item");

                            champion.getEquipment().setGloves(item);
                            user.getInventory().getItems().remove(inventoryItem.get());

                        } else if (item.getItemType().getType().equals(ItemType.LEGS.getType())) {
                            if (champion.getEquipment().getLegs() != null)
                                throw new Exception("Take off current item");

                            champion.getEquipment().setLegs(item);
                            user.getInventory().getItems().remove(inventoryItem.get());

                        } else if (item.getItemType().getType().equals(ItemType.BOOTS.getType())) {
                            if (champion.getEquipment().getBoots() != null)
                                throw new Exception("Take off current item");

                            champion.getEquipment().setBoots(item);
                            user.getInventory().getItems().remove(inventoryItem.get());
                        } else if (item.getItemType().getType().equals(ItemType.RING.getType())) {
                            if (champion.getEquipment().getRing() != null)
                                throw new Exception("Take off current item");

                            champion.getEquipment().setRing(item);
                            user.getInventory().getItems().remove(inventoryItem.get());

                        } else if (item.getItemType().getType().equals(ItemType.NECKLACE.getType())) {
                            if (champion.getEquipment().getNecklace() != null)
                                throw new Exception("Take off current item");

                            champion.getEquipment().setNecklace(item);
                            user.getInventory().getItems().remove(inventoryItem.get());

                        } else if (item.getItemType().getType().equals(ItemType.BRACELET.getType())) {
                            if (champion.getEquipment().getBracelet() != null)
                                throw new Exception("Take off current item");

                            champion.getEquipment().setBracelet(item);
                            user.getInventory().getItems().remove(inventoryItem.get());
                        }
                    }
                }
            }
            userService.save(user);
            inventoryItemService.delete(inventoryItem.get());
        }
    }

    private void throwExceptionIf_ChampionHaveNotEnoughLevel(Champion champion, Item item) throws Exception {
        if (champion.getLevel() < item.getLevel())
            throw new Exception("Champion have not enough level");
    }
}
