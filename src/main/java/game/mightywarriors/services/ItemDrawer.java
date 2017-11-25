package game.mightywarriors.services;

import game.mightywarriors.data.enums.WeaponType;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
public class ItemDrawer {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @Transactional
    public void drawItemsForUser() {
        LinkedList<User> users = userService.findAll();
        LinkedList<Item> items = itemService.findAll();

        users.forEach(user -> {
            List<Item> oldItemsInShop = new LinkedList<>(user.getShop().getItems());
            user.getShop().getItems().clear();
            int random = 0;
            while (user.getShop().getItems().size() < 10) {
                random++;

                try {
                    Item item = items.get(random);
                    if (item.getLevel() > user.getChampions().get(0).getLevel())
                        continue;

                    user = addItemForSpecificType(oldItemsInShop, user, item, WeaponType.WEAPON);
                    user = addItemForSpecificType(oldItemsInShop, user, item, WeaponType.ARMOR);
                    user = addItemForSpecificType(oldItemsInShop, user, item, WeaponType.BOOTS);
                    user = addItemForSpecificType(oldItemsInShop, user, item, WeaponType.BRACELET);
                    user = addItemForSpecificType(oldItemsInShop, user, item, WeaponType.GLOVES);
                    user = addItemForSpecificType(oldItemsInShop, user, item, WeaponType.HELMET);
                    user = addItemForSpecificType(oldItemsInShop, user, item, WeaponType.LEGS);
                    user = addItemForSpecificType(oldItemsInShop, user, item, WeaponType.NECKLACE);
                    user = addItemForSpecificType(oldItemsInShop, user, item, WeaponType.OFFHAND);
                    user = addItemForSpecificType(oldItemsInShop, user, item, WeaponType.RING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        userService.save(users);
    }

    private User addItemForSpecificType(List<Item> oldItemsInShop, User user, Item item, WeaponType weaponType) {
        for (Item x : user.getShop().getItems())
            if (x.getTypeOfWeapon().getType().equals(weaponType.getType()))
                return user;

        if (item != null)
            if (!oldItemsInShop.contains(item))
                if (!user.getShop().getItems().contains(item))
                    if (item.getTypeOfWeapon().getType().equals(weaponType.getType()))
                        user.getShop().addItem(item);
        return user;
    }

}
