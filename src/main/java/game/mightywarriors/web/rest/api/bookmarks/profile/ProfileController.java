package game.mightywarriors.web.rest.api.bookmarks.profile;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.other.enums.StatisticType;
import game.mightywarriors.services.bookmarks.profile.ChampionPointsManager;
import game.mightywarriors.services.bookmarks.profile.ItemManager;
import game.mightywarriors.web.json.objects.bookmarks.PlaceInformer;
import game.mightywarriors.web.json.objects.bookmarks.PointsInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    @Autowired
    private ChampionPointsManager championPointsManager;
    @Autowired
    private ItemManager itemManager;

    @PostMapping("secure/profile/statistic")
    public void fight(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PointsInformer informer) throws Exception {
        for (StatisticType type : StatisticType.values()) {
            if (type.getType().equals(informer.statisticName)) {
                championPointsManager.addPoints(authorization, type, informer.championId);
                return;
            }
        }
    }

    @PostMapping("secure/profile/equipmentToInventory")
    public void moveEquipmentItemToInventory(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PlaceInformer informer) throws Exception {
        itemManager.moveEquipmentItemToInventory(authorization, informer.itemId);
    }

    @PostMapping("secure/profile/inventoryToEquipment")
    public void moveInventoryToEquipmentItem(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PlaceInformer informer) throws Exception {
        itemManager.moveInventoryToEquipmentItem(authorization, informer.itemId, informer.championId);
    }
}
