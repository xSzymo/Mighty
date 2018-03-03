package game.mightywarriors.services.bookmarks.kingdom;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.Kingdom;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.KingdomInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KingdomAssigner {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private UserService userService;

    public void setKingdom(String authorization, KingdomInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_userIsNotPresent(user);
        throwExceptionIf_userAlreadyHaveKingdom(user);
        throwExceptionIf_userIsNotEnabled(user);

        user.setKingdom(informer.knight ? Kingdom.KNIGHT : Kingdom.MERCENERY);
        user.setAccountNonLocked(true);
        user.addGold(new BigDecimal(1));
        userService.save(user);
    }

    private void throwExceptionIf_userIsNotPresent(User user) throws Exception {
        if (user == null)
            throw new Exception("User not found");
    }

    private void throwExceptionIf_userAlreadyHaveKingdom(User user) throws Exception {
        if (user.getKingdom() != null)
            throw new Exception("User already have kingdom");
    }

    private void throwExceptionIf_userIsNotEnabled(User user) throws Exception {
        if (!user.isAccountEnabled())
            throw new Exception("User account is not enabled");
    }
}
