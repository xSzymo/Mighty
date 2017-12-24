package game.mightywarriors.services.background.tasks;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.WeaponType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemDrawer {
    private static Random rand;

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    private Map map;

    public ItemDrawer() {
        rand = new Random();
    }

    @Transactional
    public void drawItemsForUser() {
        LinkedList<User> users = userService.findAll();
        drawItems(users);
    }

    @Transactional
    public void drawItemsForUser(long id) {
        User one = userService.findOne(id);
        LinkedList<User> users = new LinkedList<>();
        users.add(one);

        drawItems(users);
    }

    private void drawItems(LinkedList<User> users) {
        LinkedList<Item> items = itemService.findAll();
        map = new HashMap();

        if (items.size() == 0)
            throw new RuntimeException("restart system");

        draw(users, items, true);
        draw(users, items, false);

        userService.save(users);
    }

    private LinkedList<User> draw(LinkedList<User> users, LinkedList<Item> items, boolean clear) {
        users.forEach(user -> {
            if (user.getChampions().size() == 0)
                throw new RuntimeException("restart system");

            List<Item> oldItemsInShop = new LinkedList<>(user.getShop().getItems());
            if (oldItemsInShop.size() == 0)
                oldItemsInShop = new LinkedList<>();

            if (clear)
                user.getShop().getItems().clear();

            List<Item> itemsForSpecificLevel = new LinkedList<>(getAllItemsForSpecificLevel(items, user.getUserChampiongHighestLevel()));
            while (user.getShop().getItems().size() < 10 && itemsForSpecificLevel.size() > 0) {
                Item item = null;
                try {
                    item = itemsForSpecificLevel.get(rand.nextInt(itemsForSpecificLevel.size() > 0 ? itemsForSpecificLevel.size() : 1));

                    if (item.getLevel() > user.getUserChampiongHighestLevel())
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
                } finally {
                    itemsForSpecificLevel.remove(item);
                }
            }
        });

        return users;
    }

    private List<Item> getAllItemsForSpecificLevel(LinkedList<Item> items, long level) {
        if (map.get(level) == null)
            map.put(level, items.stream().filter(x -> x.getLevel() <= level && x.getLevel() >= level - SystemVariablesManager.NUMBER_ABOVE_ITEM).collect(Collectors.toList()));

        return (List<Item>) map.get(level);
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
