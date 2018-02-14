package game.mightywarriors.services.bookmarks.guild;

import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.Request;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.GuildRole;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.services.bookmarks.utilities.GuildHelper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildRequestManager {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private GuildService guildService;
    @Autowired
    private UserService userService;
    @Autowired
    private GuildHelper helper;

    public void sendRequest(String authorization, GuildInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Guild guild = helper.retrieveGuild(informer);

        throwExceptionIf_UserAlreadySentInvite(user, guild);
        throwExceptionIf_UserIsAlreadyInGuild(user, guild);

        Request request = new Request(user, informer.description);
        guild.getInvites().add(request);
        guildService.save(guild);
    }

    public void acceptRequest(String authorization, GuildInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_userIsNotGuildOwner(user);

        User invitedUser = helper.retrieveUserFromGuildRequests(user, informer);
        Guild guild = user.getGuild();

        invitedUser.setGuild(guild);
        guild.addUser(invitedUser);
        guild.getInvites().remove(guild.getInvites().stream().filter(x -> x.getUser().getLogin().equals(invitedUser.getLogin())).findFirst().get());

        userService.save(invitedUser);
    }

    public void deleteRequest(String authorization, GuildInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_userIsNotGuildOwner(user);

        Request request = helper.retrieveRequestFromGuildRequests(user, informer);
        Guild guild = user.getGuild();
        guild.getInvites().remove(request);
        guildService.save(guild);
    }

    private void throwExceptionIf_userIsNotGuildOwner(User user) throws NoAccessException {
        if (!user.getUserRole().getRole().equals(GuildRole.OWNER.getRole()))
            throw new NoAccessException("user have no access to do that");
    }

    private void throwExceptionIf_UserIsAlreadyInGuild(User user, Guild guild) throws Exception {
        if (guild.getUsers().stream().anyMatch(x -> x.getLogin().equals(user.getLogin())))
            throw new Exception("You are already in this guild");
    }

    private void throwExceptionIf_UserAlreadySentInvite(User user, Guild guild) throws Exception {
        if (guild.getInvites().stream().anyMatch(x -> x.getUser().getLogin().equals(user.getLogin())))
            throw new Exception("You already sent a request");
    }
}
