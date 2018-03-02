package game.mightywarriors.services.bookmarks.guild;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.Request;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.GuildRole;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.services.bookmarks.utilities.GuildHelper;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.AcceptGuildRequestInformer;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import game.mightywarriors.web.json.objects.bookmarks.GuildRequestInformer;
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

    public void sendRequest(String authorization, GuildRequestInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        Guild guild = helper.retrieveGuild(informer);

        throwExceptionIf_UserAlreadySentInvite(user, guild);
        throwExceptionIf_UserHaveNotEnoughLevel(user, guild);
        throwExceptionIf_UserIsAlreadyInGuild(user, guild);
        throwExceptionIf_guildIsFromDifferentKingdom(guild.getUsers().iterator().next(), user);

        Request request = new Request(user, informer.description);
        guild.getInvites().add(request);
        guildService.save(guild);
    }

    public void acceptRequest(String authorization, AcceptGuildRequestInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);
        User invitedUser = helper.retrieveUserFromGuildRequests(user, informer);

        throwExceptionIf_userIsNotPresent(invitedUser);
        throwExceptionIf_userIsNotGuildOwner(user);
        throwExceptionIf_guildHaveAlreadyMaxUsers(user);
        throwExceptionIf_guildIsFromDifferentKingdom(user, invitedUser);

        Guild guild = user.getGuild();

        invitedUser.setGuild(guild);
        invitedUser.getChats().add(user.getGuild().getChat());
        guild.addUser(invitedUser);
        guild.getInvites().remove(guild.getInvites().stream().filter(x -> x.getUser().getLogin().equals(invitedUser.getLogin())).findFirst().get());

        userService.save(invitedUser);
    }

    public void deleteRequest(String authorization, AcceptGuildRequestInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_userIsNotGuildOwner(user);

        Request request = helper.retrieveRequestFromGuildRequests(user, informer);
        Guild guild = user.getGuild();
        guild.getInvites().remove(request);
        guildService.save(guild);
    }

    private void throwExceptionIf_guildHaveAlreadyMaxUsers(User user) throws Exception {
        if (user.getGuild().getUsers().size() >= SystemVariablesManager.MAX_USERS_IN_GUILD)
            throw new Exception("Guild has reached the limit of users");
    }

    private void throwExceptionIf_guildIsFromDifferentKingdom(User user, User invitedUser) throws Exception {
        if (!user.getKingdom().getKingdom().equals(invitedUser.getKingdom().getKingdom()))
            throw new Exception("User is from different kingdom");
    }

    private void throwExceptionIf_userIsNotGuildOwner(User user) throws NoAccessException {
        if (!user.getRole().getRole().equals(GuildRole.OWNER.getRole()))
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

    private void throwExceptionIf_UserHaveNotEnoughLevel(User user, Guild guild) throws Exception {
        if (guild.getMinimumLevel() > user.getUserChampionHighestLevel())
            throw new Exception("You have not enough level");
    }

    private void throwExceptionIf_userIsNotPresent(User user) throws Exception {
        if (user == null)
            throw new Exception("User not found");
    }
}
