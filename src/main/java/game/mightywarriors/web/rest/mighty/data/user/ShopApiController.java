package game.mightywarriors.web.rest.mighty.data.user;


import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.ShopService;
import game.mightywarriors.data.tables.Shop;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShopApiController {
    @Autowired
    private ShopService service;
    @Autowired
    private UsersRetriever retriever;

    @GetMapping("secure/shop")
    public Shop getMissionFights(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);
        return user.getShop();
    }
}
