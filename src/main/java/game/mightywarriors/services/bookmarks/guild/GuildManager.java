package game.mightywarriors.services.bookmarks.guild;

import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.services.UserRoleService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.Admin;
import game.mightywarriors.data.tables.Chat;
import game.mightywarriors.data.tables.Guild;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.other.enums.ChatRole;
import game.mightywarriors.other.enums.GuildRole;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.GuildInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildManager {
    @Autowired
    private UsersRetriever usersRetriever;
    @Autowired
    private GuildService guildService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;

    public void createGuild(String authorization, GuildInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_guildIsPresent(user);
        throwExceptionIf_guildNameIsNotPresent(informer);
        throwExceptionIf_guildNameAlreadyExist(informer);

        Chat chat = createChat(user);
        Guild guild = createGuild(user, informer, chat);

        user.setGuild(guild);
        user.addChat(chat);
        user.setUserRole(userRoleService.find(GuildRole.OWNER.getRole()));
        userService.save(user);
    }

    public void deleteGuild(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_guildIsNotPresent(user);
        throwExceptionIf_userIsNotGuildOwner(user);

        user.setUserRole(userRoleService.find("user"));
        userService.save(user);
        guildService.delete(user.getGuild());
    }

    private Chat createChat(User user) {
        Chat chat = new Chat();
        chat.getAdmins().add(new Admin(ChatRole.OWNER, user.getLogin()));

        return chat;
    }

    private Guild createGuild(User user, GuildInformer informer, Chat chat) {
        Guild guild = new Guild(informer.guildName);
        guild.setMinimumLevel(informer.minimumLevel);
        guild.addUser(user);
        guild.setChat(chat);

        return guild;
    }

    private void throwExceptionIf_userIsNotGuildOwner(User user) throws NoAccessException {
        if (!user.getUserRole().getRole().equals(GuildRole.OWNER.getRole()))
            throw new NoAccessException("user have no access to do that");
    }

    private void throwExceptionIf_guildNameIsNotPresent(GuildInformer informer) throws Exception {
        if (informer.guildName == null)
            throw new Exception("Wrong guild name");
    }

    private void throwExceptionIf_guildNameAlreadyExist(GuildInformer informer) throws Exception {
        if (guildService.find(informer.guildName) != null)
            throw new Exception("Guild already exist");
    }

    private void throwExceptionIf_guildIsNotPresent(User user) throws Exception {
        if (user.getGuild() == null)
            throw new Exception("You already have guild");
    }

    private void throwExceptionIf_guildIsPresent(User user) throws Exception {
        if (user.getGuild() != null)
            throw new Exception("You already have guild");
    }
}
