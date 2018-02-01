package game.mightywarriors.other;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserRoleService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.WeaponType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;

@Component
public class RunAtStart {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserRoleService userRoleService;

    private HashSet<User> users = new HashSet<>();
    private LinkedList<Mission> missions = new LinkedList<>();
    private LinkedList<Champion> champions = new LinkedList<>();
    private LinkedList<Image> images = new LinkedList<>();
    private LinkedList<Statistic> statistics = new LinkedList<>();
    private LinkedList<Monster> monsters = new LinkedList<>();
    private LinkedList<Shop> shops = new LinkedList<>();
    private LinkedList<Item> items = new LinkedList<>();
    private LinkedList<Equipment> equipments = new LinkedList<>();

    @PostConstruct
    public void addExampleDataForTestAtStartApplication() throws Exception {
        if (!SystemVariablesManager.INSERT_EXAMPLE_DATA)
            return;

        UserRole admin_role = new UserRole("admin");
        UserRole user_role = new UserRole("user");
        userRoleService.save(admin_role);
        userRoleService.save(user_role);


        LinkedList<Item> myItems = new LinkedList<>();
        Statistic statistic;
        Shop shop;
        User user;
        Equipment equipment;

        for (int a, i = 3; i < 15; i++) {
            itemService.save(new Item("weapon " + i, WeaponType.WEAPON, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.BOOTS, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.BRACELET, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.GLOVES, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.HELMET, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.LEGS, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.NECKLACE, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.OFFHAND, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.RING, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));
            itemService.save(new Item("weapon " + i, WeaponType.ARMOR, new Statistic(i * i, i * i, i * i, i * i, i * i, i * i), 1));

            myItems.clear();
            shop = new Shop();
            statistic = new Statistic(i * i, i * i, i * i, i * i, i * i, i * i);

            myItems.add(new Item("name" + i, WeaponType.WEAPON, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.ARMOR, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.BOOTS, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.BRACELET, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.GLOVES, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.HELMET, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.LEGS, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.NECKLACE, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.OFFHAND, statistic, i));
            myItems.add(new Item("name" + i, WeaponType.RING, statistic, i));

            a = 0;
            equipment = new Equipment();
            equipment.setWeapon(myItems.get(a++));
            equipment.setArmor(myItems.get(a++));
            equipment.setBoots(myItems.get(a++));
            equipment.setBracelet(myItems.get(a++));
            equipment.setGloves(myItems.get(a++));
            equipment.setHelmet(myItems.get(a++));
            equipment.setLegs(myItems.get(a++));
            equipment.setNecklace(myItems.get(a++));
            equipment.setOffhand(myItems.get(a++));
            equipment.setRing(myItems.get(a));
            equipments.add(equipment);

            items.addAll(myItems);
            shop.setItems(new HashSet<>(myItems));
            shops.add(shop);
        }

        for (int i = 0; i < 12; i++) {
            statistics.add(new Statistic(2, 2, 5, 0, 2, 2));
            images.add(new Image("https://cdn.orkin.com/images/rodents/norway-rat-illustration_360x236.jpg"));
            monsters.add(new Monster(statistics.get(i), images.get(i)));
            if (i <= 5 && i > 0)
                missions.add(new Mission(1 + i, "", new BigDecimal("1"), monsters.get(i)).setImage(new Image("https://www.ufopaedia.org/images/6/62/XCOM_Base_Defense_Mission_Screen_(EU2012).png")).setDuration((30 + i * 3)));
            else if (i <= 9 && i > 5)
                missions.add(new Mission(1 + i, "", new BigDecimal("1"), monsters.get(i)).setImage(new Image("https://c1.staticflickr.com/5/4059/4485522732_f73ce8d7f7_b.jpg")).setDuration((30 + i * 3)));
            else
                missions.add(new Mission(1 + i, "", new BigDecimal("1"), monsters.get(i)).setImage(new Image("http://dclegends.wiki/images/thumb/0/0e/Alliance_Missions.jpg/300px-Alliance_Missions.jpg")).setDuration((30 + i * 3)));
        }

        for (int i = 0; i < 12; i++) {
            statistics.add(new Statistic());
            images.add(new Image("http://beaver.blox.pl/resource/bober22.jpg"));
            champions.add(new Champion(statistics.get(i), images.get(i), equipments.get(i)));
        }

        for (int a = 0, b = 0, i = 0; i < 4; i++) {
            if (i % 2 == 0)
                user = new User("admin" + i, "admin", "email", admin_role);
            else
                user = new User("user" + i, "user", "eMail");
            Image image = new Image("https://www.reduceimages.com/img/image-after.jpg");
            images.add(image);
            user.setImage(image);
            user.setShop(shops.get(i));
            user.addChampion(champions.get(a++));
            user.addChampion(champions.get(a++));
            user.addChampion(champions.get(a++));
            user.getMissions().add(missions.get(b++));
            user.getMissions().add(missions.get(b++));
            user.getMissions().add(missions.get(b++));

            users.add(user);
        }
        userService.save(users);
    }
}
