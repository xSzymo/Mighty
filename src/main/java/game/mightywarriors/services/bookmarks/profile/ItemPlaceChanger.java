package game.mightywarriors.services.bookmarks.profile;

import game.mightywarriors.data.services.InventoryItemService;
import game.mightywarriors.data.tables.InventoryItem;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ItemPlaceChanger {
    private UsersRetriever usersRetriever;
    private InventoryItemService inventoryItemService;

    @Autowired
    public ItemPlaceChanger(UsersRetriever usersRetriever, InventoryItemService inventoryItemService) {
        this.usersRetriever = usersRetriever;
        this.inventoryItemService = inventoryItemService;
    }

    public void changePlace(String authorization, int oldPosition, int newPosition) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Set<InventoryItem> items = user.getInventory().getItems();
        Optional<InventoryItem> first = items.stream().filter(x -> x.getPosition() == oldPosition).findFirst();
        Optional<InventoryItem> second = items.stream().filter(x -> x.getPosition() == newPosition).findFirst();

        throwExceptionIf_ItemIsNotPresent(first);

        first.get().setPosition(newPosition);
        inventoryItemService.save(first.get());
        if (second.isPresent()) {
            second.get().setPosition(oldPosition);
            inventoryItemService.save(second.get());
        }
    }

    private void throwExceptionIf_ItemIsNotPresent(Optional<InventoryItem> inventoryItem) throws Exception {
        if (!inventoryItem.isPresent())
            throw new Exception("Item not found");
    }
}
