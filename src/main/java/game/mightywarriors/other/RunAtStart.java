package game.mightywarriors.other;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.data.services.*;
import game.mightywarriors.data.tables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.LinkedList;

@Component
public class RunAtStart {

    private UserRole admin_role;
    private UserRole user_role;
    @Autowired
    private UserService userService;
    @Autowired
    private ChampionService championService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private UserRoleService userRoleService;

    private LinkedList<User> users = new LinkedList<>();
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
        if(SystemVariablesManager.RUNNING_TESTS == true)
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

        for (int a = 0, i = 3; i < 15; i++) {
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
            equipment.setRing(myItems.get(a++));
            equipments.add(equipment);

            items.addAll(myItems);
            shop.setItems(myItems);
            shops.add(shop);
        }

        for (int i = 0; i < 12; i++) {
            statistics.add(new Statistic());
            images.add(new Image("https://cdn.orkin.com/images/rodents/norway-rat-illustration_360x236.jpg"));
            monsters.add(new Monster(statistics.get(i), images.get(i)));
            missions.add(new Mission(1, "", new BigDecimal("1"), monsters.get(i)));
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
                user = new User("user" + i, "user", "eMail", user_role);
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
