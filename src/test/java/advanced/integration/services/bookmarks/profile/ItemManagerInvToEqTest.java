package advanced.integration.services.bookmarks.profile;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.WeaponType;
import game.mightywarriors.services.bookmarks.profile.ItemManager;
import game.mightywarriors.services.security.UsersRetriever;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ItemManagerInvToEqTest extends AuthorizationConfiguration {
    @Autowired
    private ItemManager objectUnderTest;
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private ItemService itemService;

    private User user;
    private Item item;
    private Champion champion;

    @Test
    public void moveEquipmentItemToInventory_ring() throws Exception {
        item = new Item(WeaponType.RING);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        user.getInventory().addItem(item);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setRing(null);
        userService.save(user);

        objectUnderTest.moveInventoryToEquipmentItem(token, item.getId(), champion.getId());

        user = usersRetriever.retrieveUser(token);
        assertFalse(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertNotNull(user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getRing());
    }

    @Test
    public void moveEquipmentItemToInventory_necklace() throws Exception {
        item = new Item(WeaponType.NECKLACE);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        user.getInventory().addItem(item);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setNecklace(null);
        userService.save(user);

        objectUnderTest.moveInventoryToEquipmentItem(token, item.getId(), champion.getId());

        user = usersRetriever.retrieveUser(token);
        assertFalse(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertNotNull(user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getRing());
    }

    @Test
    public void moveEquipmentItemToInventory_bracelet() throws Exception {
        item = new Item(WeaponType.BRACELET);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        user.getInventory().addItem(item);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setBracelet(null);
        userService.save(user);

        objectUnderTest.moveInventoryToEquipmentItem(token, item.getId(), champion.getId());

        user = usersRetriever.retrieveUser(token);
        assertFalse(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertNotNull(user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getRing());
    }

    @Test
    public void moveEquipmentItemToInventory_boots() throws Exception {
        item = new Item(WeaponType.BOOTS);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        user.getInventory().addItem(item);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setBoots(null);
        userService.save(user);

        objectUnderTest.moveInventoryToEquipmentItem(token, item.getId(), champion.getId());

        user = usersRetriever.retrieveUser(token);
        assertFalse(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertNotNull(user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getRing());
    }

    @Test
    public void moveEquipmentItemToInventory_legs() throws Exception {
        item = new Item(WeaponType.LEGS);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        user.getInventory().addItem(item);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setLegs(null);
        userService.save(user);

        objectUnderTest.moveInventoryToEquipmentItem(token, item.getId(), champion.getId());

        user = usersRetriever.retrieveUser(token);
        assertFalse(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertNotNull(user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getRing());
    }

    @Test
    public void moveEquipmentItemToInventory_gloves() throws Exception {
        item = new Item(WeaponType.GLOVES);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        user.getInventory().addItem(item);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setGloves(null);
        userService.save(user);

        objectUnderTest.moveInventoryToEquipmentItem(token, item.getId(), champion.getId());

        user = usersRetriever.retrieveUser(token);
        assertFalse(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertNotNull(user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getRing());
    }

    @Test
    public void moveEquipmentItemToInventory_armor() throws Exception {
        item = new Item(WeaponType.ARMOR);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        user.getInventory().addItem(item);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setArmor(null);
        userService.save(user);

        objectUnderTest.moveInventoryToEquipmentItem(token, item.getId(), champion.getId());

        user = usersRetriever.retrieveUser(token);
        assertFalse(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertNotNull(user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getRing());
    }

    @Test
    public void moveEquipmentItemToInventory_helmet() throws Exception {
        item = new Item(WeaponType.HELMET);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        user.getInventory().addItem(item);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setHelmet(null);
        userService.save(user);

        objectUnderTest.moveInventoryToEquipmentItem(token, item.getId(), champion.getId());

        user = usersRetriever.retrieveUser(token);
        assertFalse(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertNotNull(user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getRing());
    }

    @Test
    public void moveEquipmentItemToInventory_offhand() throws Exception {
        item = new Item(WeaponType.OFFHAND);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        user.getInventory().addItem(item);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setOffhand(null);
        userService.save(user);

        objectUnderTest.moveInventoryToEquipmentItem(token, item.getId(), champion.getId());

        user = usersRetriever.retrieveUser(token);
        assertFalse(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertNotNull(user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getRing());
    }

    @Test
    public void moveEquipmentItemToInventory_weapon() throws Exception {
        item = new Item(WeaponType.WEAPON);
        itemService.save(item);
        user = usersRetriever.retrieveUser(token);
        user.getInventory().addItem(item);
        champion = user.getChampions().iterator().next();
        champion.getEquipment().setWeapon(null);
        userService.save(user);

        objectUnderTest.moveInventoryToEquipmentItem(token, item.getId(), champion.getId());

        user = usersRetriever.retrieveUser(token);
        assertFalse(user.getInventory().getItems().stream().anyMatch(x -> x.getId().equals(item.getId())));
        assertNotNull(user.getChampions().stream().filter(x -> x.getId().equals(champion.getId())).findFirst().get().getEquipment().getRing());
    }
}