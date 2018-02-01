package advanced.integration.services.bookmarks.profile;

import advanced.integration.config.AuthorizationConfiguration;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ItemType;
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
        item = new Item(ItemType.RING);
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
        item = new Item(ItemType.NECKLACE);
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
        item = new Item(ItemType.BRACELET);
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
        item = new Item(ItemType.BOOTS);
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
        item = new Item(ItemType.LEGS);
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
        item = new Item(ItemType.GLOVES);
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
        item = new Item(ItemType.ARMOR);
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
        item = new Item(ItemType.HELMET);
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
        item = new Item(ItemType.OFFHAND);
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
        item = new Item(ItemType.WEAPON);
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