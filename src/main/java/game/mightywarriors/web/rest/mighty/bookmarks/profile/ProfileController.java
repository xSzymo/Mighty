package game.mightywarriors.web.rest.mighty.bookmarks.profile;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.ChampionService;
import game.mightywarriors.data.tables.Champion;
import game.mightywarriors.data.tables.Statistic;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.StatisticType;
import game.mightywarriors.services.bookmarks.profile.ChampionPointsManager;
import game.mightywarriors.services.bookmarks.profile.ItemManager;
import game.mightywarriors.services.bookmarks.profile.ItemPlaceChanger;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ItemInformer;
import game.mightywarriors.web.json.objects.bookmarks.PlaceChangerInformer;
import game.mightywarriors.web.json.objects.bookmarks.PlaceInformer;
import org.springframework.beans.factory.annotation.Autowired;
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

    @CrossOrigin
    @PatchMapping("statistics/{id}")
    public void addPoints(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody Statistic statistic, @PathVariable("id") String id) throws Exception {
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
        if (statistic.getCriticalChance() > champ.getStatistic().getCriticalChance())
            championPointsManager.addPoints(authorization, StatisticType.CRITICAL_CHANCE, champ.getId(), statistic.getCriticalChance() - champ.getStatistic().getCriticalChance());
        if (statistic.getArmor() > champ.getStatistic().getArmor())
            championPointsManager.addPoints(authorization, StatisticType.ARMOR, champ.getId(), statistic.getArmor() - champ.getStatistic().getArmor());
        if (statistic.getMagicResist() > champ.getStatistic().getMagicResist())
            championPointsManager.addPoints(authorization, StatisticType.MAGIC_RESIST, champ.getId(), statistic.getMagicResist() - champ.getStatistic().getMagicResist());
    }

    @PostMapping("secure/profile/equipmentToInventory")
    public void moveEquipmentItemToInventory(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody ItemInformer informer) throws Exception {
        itemManager.moveEquipmentItemToInventory(authorization, informer.itemId);
    }

    @PostMapping("secure/profile/inventoryToEquipment")
    public void moveInventoryToEquipmentItem(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PlaceInformer informer) throws Exception {
        itemManager.moveInventoryToEquipmentItem(authorization, informer.itemId, informer.championId);
    }

    @PostMapping("secure/profile/item/change/place")
    public void changeItemPlace(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody PlaceChangerInformer informer) throws Exception {
        itemPlaceChanger.changePlace(authorization, informer.oldPosition, informer.newPosition);
    }
}
