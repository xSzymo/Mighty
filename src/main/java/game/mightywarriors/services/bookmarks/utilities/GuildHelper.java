package game.mightywarriors.services.bookmarks.utilities;

import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.Request;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildHelper {
    @Autowired
    private GuildService guildService;
    @Autowired
    private UserService userService;

    public Guild retrieveGuild(GuildInformer informer) {
        if (informer.guildName != null)
            return guildService.find(informer.guildName);
        else if (informer.guildId != 0)
            return guildService.find(informer.guildId);
        else
            return null;
    }

    public User retrieveUser(GuildInformer informer) {
        if (informer.userName != null)
            return userService.find(informer.userName);
        else if (informer.userId != 0)
            return userService.find(informer.userId);
        else
            return null;
    }

    public User retrieveUserFromGuildRequests(User user, GuildInformer informer) {
        if (informer.userName != null)
            return userService.find(informer.userName);

        if (informer.requestId != 0)
            for (Request request : user.getGuild().getInvites())
                if (request.getId() == informer.requestId)
                    return request.getUser();

        return null;
    }

    public Request retrieveRequestFromGuildRequests(User user, GuildInformer informer) {
        for (Request request : user.getGuild().getInvites())
            if (request.getId() == informer.requestId)
                return request;

        if (informer.userName != null)
            for (Request request : user.getGuild().getInvites())
                if (request.getUser().getLogin().equals(informer.userName))
                    return request;

        return null;
    }

    public boolean isUserInGuild(Guild guild, User user) {
        for (User guildUser : guild.getUsers())
            if (guildUser.getLogin().equals(user.getLogin()))
                return true;

        return false;
    }
}
