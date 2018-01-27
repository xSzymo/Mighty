package game.mightywarriors.web.rest.bookmarks.shop;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.shop.ShopManager;
import game.mightywarriors.web.json.objects.bookmarks.ShopInformer;
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


    @PostMapping("secure/shop/sell")
    public void sellItem(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody ShopInformer informer) throws Exception {

        manager.sellItem(authorization, informer);
    }
}
