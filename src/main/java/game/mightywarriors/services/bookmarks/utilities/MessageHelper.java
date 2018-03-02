package game.mightywarriors.services.bookmarks.utilities;

import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.web.json.objects.bookmarks.PrivilegesInformer;
import game.mightywarriors.web.json.objects.bookmarks.PrivilegesWithOutAdminInformer;
import game.mightywarriors.web.json.objects.bookmarks.UserMessageInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageHelper {
    @Autowired
    private UserService userService;

    public User getUserFromInformer(PrivilegesInformer informer) {
        return getUserFromInformer(new UserMessageInformer(informer.userId, informer.userLogin));
    }

    public User getUserFromInformer(PrivilegesWithOutAdminInformer informer) {
        return getUserFromInformer(new UserMessageInformer(informer.userId, informer.userLogin));
    }

    public User getUserFromInformer(UserMessageInformer informer) {
        if (informer.userLogin != null)
            return userService.find(informer.userLogin);
        else if (informer.userId != 0)
            return userService.find(informer.userId);
        else
            return null;
    }
}
