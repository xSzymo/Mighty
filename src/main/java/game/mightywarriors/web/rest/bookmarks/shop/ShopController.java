package game.mightywarriors.web.rest.bookmarks.shop;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.tables.MissionFight;
import game.mightywarriors.services.bookmarks.shop.ShopManager;
import game.mightywarriors.services.bookmarks.tavern.MissionFightChecker;
import game.mightywarriors.services.bookmarks.tavern.TavernManager;
import game.mightywarriors.web.json.objects.bookmarks.tavern.Informer;
import game.mightywarriors.web.json.objects.bookmarks.tavern.ShopInformer;
import game.mightywarriors.web.json.objects.fights.FightResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopController {
    @Autowired
    private ShopManager manager;

    @PostMapping("secure/shop/buy")
    public void buyItem(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody ShopInformer informer) throws Exception {

        manager.buyItem(authorization, informer);
    }
}
