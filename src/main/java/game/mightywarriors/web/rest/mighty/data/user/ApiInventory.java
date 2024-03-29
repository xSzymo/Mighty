package game.mightywarriors.web.rest.mighty.data.user;


import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.InventoryService;
import game.mightywarriors.data.tables.Inventory;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiInventory {
    @Autowired
    private UsersRetriever retriever;
    @Autowired
    private InventoryService service;

    @GetMapping("secure/inventory")
    public Inventory getMissionFights(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);
        return user.getInventory();
    }
}
