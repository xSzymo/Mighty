package game.mightywarriors.services.background.tasks;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
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

    private static long howManyItemsForOneChampion = SystemVariablesManager.HOW_MANY_ITEMS_FOR_ONE_CHAMPION;

    public ItemDrawer() {
        rand = new Random();
    }

    @Transactional
    public synchronized void drawItemsForUser() {
        HashSet<User> users = userService.findAll();
        drawItems(users);
    }

    @Transactional
    public synchronized void drawItemsForUser(long id) {
        User one = userService.find(id);
        HashSet<User> users = new HashSet<>();
        users.add(one);

        drawItems(users);
    }

    private synchronized void drawItems(Set<User> users) {
        HashSet<Item> items = itemService.findAll();
        HashMap map = new HashMap();

        if (items.size() == 0)
            throw new RuntimeException("restart system");

        draw(users, items, map, true);

        userService.save(users);
    }

    private Set<User> draw(Set<User> users, Set<Item> items, HashMap map, boolean clear) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();

            if (clear)
                user.getShop().getItems().clear();

            Iterator<Champion> champions = user.getChampions().iterator();
            for (int i = 1; i <= user.getChampions().size(); i++) {
                Champion champion = champions.next();
                draw(user, items, map, champion.getLevel(), i * howManyItemsForOneChampion, (i * howManyItemsForOneChampion) - howManyItemsForOneChampion, i);
            }
        }
        return users;
    }

    private void draw(User user, Set<Item> items, HashMap map, long limitedLevel, long maxItems, long minItems, int times) {
        if (user.getChampions().size() == 0)
            throw new RuntimeException("restart system");

        List<Item> oldItemsInShop = new LinkedList<>(user.getShop().getItems());
        if (oldItemsInShop.size() == 0)
            oldItemsInShop = new LinkedList<>();

        List<Item> itemsForSpecificLevel = new LinkedList<>(getAllItemsForSpecificLevel(items, map, limitedLevel));
        while (user.getShop().getItems().size() < maxItems && itemsForSpecificLevel.size() > minItems) {
            Item item = null;
            try {
                item = itemsForSpecificLevel.get(rand.nextInt(itemsForSpecificLevel.size() > 0 ? itemsForSpecificLevel.size() : 1));

                user = addItemForSpecificType(oldItemsInShop, user, item, times, WeaponType.WEAPON);
                user = addItemForSpecificType(oldItemsInShop, user, item, times, WeaponType.ARMOR);
                user = addItemForSpecificType(oldItemsInShop, user, item, times, WeaponType.BOOTS);
                user = addItemForSpecificType(oldItemsInShop, user, item, times, WeaponType.BRACELET);
                user = addItemForSpecificType(oldItemsInShop, user, item, times, WeaponType.GLOVES);
                user = addItemForSpecificType(oldItemsInShop, user, item, times, WeaponType.HELMET);
                user = addItemForSpecificType(oldItemsInShop, user, item, times, WeaponType.LEGS);
                user = addItemForSpecificType(oldItemsInShop, user, item, times, WeaponType.NECKLACE);
                user = addItemForSpecificType(oldItemsInShop, user, item, times, WeaponType.OFFHAND);
                user = addItemForSpecificType(oldItemsInShop, user, item, times, WeaponType.RING);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                itemsForSpecificLevel.remove(item);
            }
        }
    }


    private List<Item> getAllItemsForSpecificLevel(Set<Item> items, HashMap map, long level) {
        if (map.get(level) == null)
            map.put(level, items.stream().filter(x -> x.getLevel() <= level && x.getLevel() >= level - SystemVariablesManager.NUMBER_ABOVE_ITEM).collect(Collectors.toList()));

        return (List<Item>) map.get(level);
    }

    private User addItemForSpecificType(List<Item> oldItemsInShop, User user, Item item, int times, WeaponType weaponType) {
        int counter = 0;
        for (Item x : user.getShop().getItems())
            if (x.getWeaponType().getType().equals(weaponType.getType()))
                counter++;

        if (counter >= times)
            return user;

        if (item != null)
            if (!oldItemsInShop.contains(item))
                if (!user.getShop().getItems().contains(item))
                    if (item.getWeaponType().getType().equals(weaponType.getType()))
                        user.getShop().addItem(item);
        return user;
    }
}
