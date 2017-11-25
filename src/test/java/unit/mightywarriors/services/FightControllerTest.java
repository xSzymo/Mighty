package unit.mightywarriors.services;

import game.mightywarriors.data.enums.WeaponType;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.services.FightReferee;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FightControllerTest {

    private FightReferee fightReferee = new FightReferee();

    @Test
    public void checkIsUserStrongerThanMonster() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setRing(new Item(WeaponType.RING, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(2, 5, 7, 9, 10, 12), 1));

        user.getChampions().add(new Champion(new Statistic(2, 2, 3, 5, 5, 7), equipment));

        Monster monster = new Monster(new Statistic(25, 10, 20, 5, 15, 15));
        monster.setLevel(5);

        assertTrue(fightReferee.checkIsUserStrongerThanMonster(user, monster));
    }

    @Test
    public void checkWhoIsStronger() throws Exception {
        User user = new User("admin", "admin", "admin");

        Equipment equipment = new Equipment();
        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setRing(new Item(WeaponType.RING, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(2, 5, 7, 9, 10, 12), 1));

        user.getChampions().add(new Champion(new Statistic(2, 2, 3, 5, 5, 7), equipment));


        User user1 = new User("admin", "admin", "admin");

        equipment = new Equipment();
        equipment.setWeapon(new Item(WeaponType.WEAPON, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setArmor(new Item(WeaponType.ARMOR, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setRing(new Item(WeaponType.RING, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setOffhand(new Item(WeaponType.OFFHAND, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setNecklace(new Item(WeaponType.NECKLACE, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setLegs(new Item(WeaponType.LEGS, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setHelmet(new Item(WeaponType.HELMET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setGloves(new Item(WeaponType.GLOVES, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBracelet(new Item(WeaponType.BRACELET, new Statistic(2, 5, 7, 9, 10, 12), 1));
        equipment.setBoots(new Item(WeaponType.BOOTS, new Statistic(2, 5, 7, 9, 10, 12), 1));

        user1.getChampions().add(new Champion(new Statistic(1, 2, 3, 5, 5, 7), equipment));


        assertEquals(user, fightReferee.checkWhoIsStronger(user, user1));
    }


    @Test
    public void checkUsersWithSameStrength() throws Exception {
        User user = new User("admin", "admin", "admin");
        user.getChampions().add(new Champion(new Statistic(2, 2, 3, 5, 5, 7), new Equipment()));

        User user1 = new User("admin", "admin", "admin");
        user1.getChampions().add(new Champion(new Statistic(2, 2, 3, 5, 5, 7), new Equipment()));


        assertEquals(null, fightReferee.checkWhoIsStronger(user, user1));
    }
}
