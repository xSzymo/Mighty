package game.mightywarriors.web.rest.mighty.bookmarks.profile;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.services.InventoryItemService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.InventoryItem;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.StatisticType;
import game.mightywarriors.services.bookmarks.profile.ChampionPointsManager;
import game.mightywarriors.services.bookmarks.profile.ItemManager;
import game.mightywarriors.services.bookmarks.profile.ItemPlaceChanger;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ItemInformer;
import game.mightywarriors.web.json.objects.bookmarks.PlaceInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {
    @Autowired
    private ChampionPointsManager championPointsManager;
    @Autowired
    private ItemManager itemManager;
    @Autowired
    private ItemPlaceChanger itemPlaceChanger;
    @Autowired
    private ChampionService championService;
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private InventoryItemService inventoryItemService;

    @CrossOrigin
    @PatchMapping("statistics/{id}")
    public ResponseEntity<String> addPoints(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody Statistic statistic, @PathVariable("id") String id) {
        try {
            User user = usersRetriever.retrieveUser(authorization);
            Champion champ = championService.findByStatisticId(Long.parseLong(id));
            if (user.getChampions().stream().noneMatch(x -> x.getId().equals(champ.getId())))
                throw new Exception("User have no access to this champion");

            if (statistic.getStrength() > champ.getStatistic().getStrength())
                championPointsManager.addPoints(authorization, StatisticType.STRENGTH, champ.getId(), statistic.getStrength() - champ.getStatistic().getStrength());
            if (statistic.getIntelligence() > champ.getStatistic().getIntelligence())
                championPointsManager.addPoints(authorization, StatisticType.INTELLIGENCE, champ.getId(), statistic.getIntelligence() - champ.getStatistic().getIntelligence());
            if (statistic.getVitality() > champ.getStatistic().getVitality())
                championPointsManager.addPoints(authorization, StatisticType.VITALITY, champ.getId(), statistic.getVitality() - champ.getStatistic().getVitality());
            if (statistic.getArmor() > champ.getStatistic().getArmor())
                championPointsManager.addPoints(authorization, StatisticType.ARMOR, champ.getId(), statistic.getArmor() - champ.getStatistic().getArmor());
            if (statistic.getMagicResist() > champ.getStatistic().getMagicResist())
                championPointsManager.addPoints(authorization, StatisticType.MAGIC_RESIST, champ.getId(), statistic.getMagicResist() - champ.getStatistic().getMagicResist());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @CrossOrigin
    @PatchMapping("inventoryItems/{id}")
    public ResponseEntity<String> changeItemPlace(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody InventoryItem inventoryItem, @PathVariable("id") String id) {
        try {
            User user = usersRetriever.retrieveUser(authorization);
            if (user.getInventory().getItems().stream().noneMatch(x -> x.getId().equals(Long.parseLong(id))))
                throw new Exception("User have no access to this item");

            itemPlaceChanger.changePlace(authorization, inventoryItemService.find(Long.parseLong(id)).getPosition(), inventoryItem.getPosition());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("secure/profile/equipmentToInventory")
    public ResponseEntity<String> moveEquipmentItemToInventory(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody ItemInformer informer) {
        try {
            itemManager.moveEquipmentItemToInventory(authorization, informer.itemId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PostMapping("secure/profile/inventoryToEquipment")
    public ResponseEntity<String> moveInventoryToEquipmentItem(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PlaceInformer informer) throws Exception {
        try {
            itemManager.moveInventoryToEquipmentItem(authorization, informer.itemId, informer.championId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
