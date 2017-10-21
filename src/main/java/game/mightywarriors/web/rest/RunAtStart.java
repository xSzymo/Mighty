package game.mightywarriors.web.rest;

import game.mightywarriors.data.enums.WeaponType;
import game.mightywarriors.data.repositories.*;
import game.mightywarriors.data.tables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RunAtStart {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @PostConstruct
    public void halaso() {
        UserRole admin_role = new UserRole("admin");
        UserRole user_role = new UserRole("user");

        userRoleRepository.save(admin_role);
        userRoleRepository.save(user_role);

        for(int i = 0; i < 10; i ++) {
            Statistic statistic1 = new Statistic();
            statisticRepository.save(statistic1);

            Item item = new Item("sword" + i, WeaponType.WEAPON, statistic1, 1);
            itemRepository.save(item);

            User user = new User("loing_" + i, "password_" + i, "mail@" + i);
            Image image = new Image("C:/folder/", "name"+ i, "png");
            imageRepository.save(image);

            Statistic statistic = new Statistic();
            statisticRepository.save(statistic);

            Shop shop = new Shop();
            shop.addItem(item);
            shopRepository.save(shop);

            Equipment equipment = new Equipment();
            equipment.setWeapon(item);
            equipmentRepository.save(equipment);

            Champion champion = new Champion(statistic, shop, equipment);
            championRepository.save(champion);

            user.setImage(image);
            user.setChampion(champion);
            userRepository.save(user);
            if(i % 2 == 0) {
                user_role.getUsers().add(user);
                userRoleRepository.save(user_role);
            } else {
                admin_role.getUsers().add(user);
                userRoleRepository.save(admin_role);
            }
        }
        halo();
    }

    public void halo() {
        UserRole admin_role = new UserRole("admin");

            Statistic statistic1 = new Statistic();
            statisticRepository.save(statistic1);

            Item item = new Item("sworderino", WeaponType.WEAPON, statistic1, 1);
            itemRepository.save(item);

            User user = new User("admin", "admin", "admin@");
            Image image = new Image("C:/folder/", "name01" , ".png");
            imageRepository.save(image);

            Statistic statistic = new Statistic();
            statisticRepository.save(statistic);

            Shop shop = new Shop();
            shop.addItem(item);
            shopRepository.save(shop);

            Equipment equipment = new Equipment();
            equipment.setWeapon(item);
            equipmentRepository.save(equipment);

            Champion champion = new Champion(statistic, shop, equipment);
            championRepository.save(champion);

            user.setImage(image);
            user.setChampion(champion);
            userRepository.save(user);
            admin_role.getUsers().add(user);
            userRoleRepository.save(admin_role);
    }
}
