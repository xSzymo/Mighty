package game.mightywarriors.other;

import game.mightywarriors.data.enums.WeaponType;
import game.mightywarriors.data.repositories.*;
import game.mightywarriors.data.tables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RunAtStart {
    UserRole admin_role;
    UserRole user_role;
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

  //  @PostConstruct
    public void halaso() {
        admin_role = new UserRole("admin");
        user_role = new UserRole("user");

        userRoleRepository.save(admin_role);
        userRoleRepository.save(user_role);

        for (int i = 0; i < 10; i++) {
            Statistic statistic1 = new Statistic();
            statisticRepository.save(statistic1);

            Item item = new Item("sword" + i, WeaponType.WEAPON, statistic1, 1);
            Image ima = new Image("http://d20pfsrd.opengamingnetwork.com/wp-content/uploads/sites/12/2017/01/EXsword3.jpg");
            imageRepository.save(ima);
            item.setImage(ima);
            itemRepository.save(item);

            User user;

            if (i % 2 == 0) {
                user = new User("loing_" + i, "password_" + i, "mail@" + i, user_role);
            } else {
                user = new User("loing_" + i, "password_" + i, "mail@" + i, admin_role);
            }
            Image image = new Image("http://images81.fotosik.pl/231/29440c344295bb2amed.jpg");
            if (i == 0)
                image = new Image("https://cdn.orkin.com/images/rodents/norway-rat-illustration_360x236.jpg");
            imageRepository.save(image);

            Statistic statistic = new Statistic();
            statisticRepository.save(statistic);

            Shop shop = new Shop();
            shop.addItem(item);
            shopRepository.save(shop);

            Equipment equipment = new Equipment();
            equipment.setWeapon(item);
            equipmentRepository.save(equipment);

            Champion champion = new Champion(statistic, equipment);
            championRepository.save(champion);

            // user.setImage(image);
            user.setShop(shop);
            user.addChampion(champion);
            userRepository.save(user);
        }
        halo();
    }

    public void halo() {
        userRoleRepository.save(admin_role);

        Statistic statistic1 = new Statistic();
        statisticRepository.save(statistic1);

        Item item = new Item("sworderino", WeaponType.WEAPON, statistic1, 1);
        itemRepository.save(item);

        User user = new User("admin", "admin", "admin@", admin_role);
        Image image = new Image("C:/folde.png");
        imageRepository.save(image);

        Statistic statistic = new Statistic();
        statisticRepository.save(statistic);

        Shop shop = new Shop();
        shop.addItem(item);
        shopRepository.save(shop);

        Equipment equipment = new Equipment();
        equipment.setWeapon(item);
        equipmentRepository.save(equipment);

        Champion champion = new Champion(statistic, equipment);
        championRepository.save(champion);

        //    user.setImage(image);
        user.addChampion(champion);
        user.setUserRole(admin_role);
        userRepository.save(user);
//            admin_role.getUsers().add(user);
//            userRoleRepository.save(admin_role);
    }
}
