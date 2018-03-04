package game.mightywarriors.services.bookmarks.shop;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.ItemService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Item;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.exceptions.NotEnoughGoldException;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.ShopInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ShopManager {
    private UserService userService;
    private UsersRetriever usersRetriever;
    private ItemService itemService;

    @Autowired
    public ShopManager(UserService userService, UsersRetriever usersRetriever, ItemService itemService) {
        this.userService = userService;
        this.usersRetriever = usersRetriever;
        this.itemService = itemService;
    }

    public void buyItem(String authorization, ShopInformer shopInformer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Item item = itemService.find(shopInformer.itemId);

        throwExceptionIf_ShopDoesNotHaveSpecificItem(user, item);
        throwExceptionIf_UserHaveNotEnoughGold(user, item);

        user.subtractGold(item.getGold());
        user.getInventory().addItem(item);
        user.getShop().getItems().remove(user.getShop().getItems().stream().filter(x -> x.getId().equals(item.getId())).findFirst().get());

        userService.save(user);
    }

    public void sellItem(String authorization, ShopInformer shopInformer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Item item = itemService.find(shopInformer.itemId);

        throwExceptionIf_UserHaveNotSpecificItem(user, item);

        user.addGold(new BigDecimal(item.getGold().doubleValue() * SystemVariablesManager.PERCENT_FOR_SOLD_ITEM));
        user.getInventory().getItems().remove(item);

        userService.save(user);
    }

    private void throwExceptionIf_ShopDoesNotHaveSpecificItem(User user, Item item) throws Exception {
        if (user.getShop().getItems().stream().noneMatch(x -> x.getId().equals(item.getId())))
            throw new Exception("Wrong item");
    }

    private void throwExceptionIf_UserHaveNotEnoughGold(User user, Item item) throws Exception {
        if (user.getGold().doubleValue() < item.getGold().doubleValue())
            throw new NotEnoughGoldException("Not enough gold");
    }

    private void throwExceptionIf_UserHaveNotSpecificItem(User user, Item item) throws Exception {
        if (!user.getInventory().getItems().contains(item))
            throw new Exception("Wrong item");
    }
}
