package game.mightywarriors.web.rest.mighty.data.user;


import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.InventoryItemService;
import game.mightywarriors.data.tables.InventoryItem;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Stream;

@RestController
public class InventoryItemApiController {
    @Autowired
    private InventoryItemService inventoryItemService;
    @Autowired
    private UsersRetriever retriever;


    @GetMapping("secure/inventoryItems")
    public Set<InventoryItem> getInventoryItems(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);

        return user.getInventory().getItems();
    }

    @GetMapping("inventoryItems/{id}")
    public Stream<InventoryItem> getInventoryItem(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @PathVariable("id") String id) throws Exception {
        User user = retriever.retrieveUser(authorization);

        return user.getInventory().getItems().stream().filter(x -> x.getId().equals(Long.parseLong(id)));
    }
}
