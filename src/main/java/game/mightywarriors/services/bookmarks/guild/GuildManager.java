package game.mightywarriors.services.bookmarks.guild;

import game.mightywarriors.data.services.GuildService;
import game.mightywarriors.data.services.RoleService;
import game.mightywarriors.data.services.UserService;
import game.mightywarriors.data.tables.*;
import game.mightywarriors.other.enums.ChatRole;
import game.mightywarriors.other.enums.GuildRole;
import game.mightywarriors.other.exceptions.NoAccessException;
import game.mightywarriors.services.security.UsersRetriever;
import game.mightywarriors.web.json.objects.bookmarks.CreateGuildInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildManager {
    private UsersRetriever usersRetriever;
    private GuildService guildService;
    private RoleService roleService;
    private UserService userService;

    @Autowired
    public GuildManager(UsersRetriever usersRetriever, GuildService guildService, RoleService roleService, UserService userService) {
        this.usersRetriever = usersRetriever;
        this.guildService = guildService;
        this.roleService = roleService;
        this.userService = userService;
    }

    public void createGuild(String authorization, CreateGuildInformer informer) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_GuildIsPresent(user);
        throwExceptionIf_GuildNameIsNotPresent(informer);
        throwExceptionIf_GuildNameAlreadyExist(informer);

        Role role = roleService.find(GuildRole.OWNER.getRole());
        role.getUsers().add(user);
        roleService.save(role);

        Chat chat = createChat(user);
        Guild guild = createGuild(user, informer, chat);
        guildService.save(guild);

        user.setRole(roleService.find(role));
        user.setGuild(guild);
        user.addChat(chat);
        userService.save(user);
    }

    public void deleteGuild(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_GuildIsNotPresent(user);
        throwExceptionIf_UserIsNotGuildOwner(user);

        user.setRole(roleService.find("user"));
        userService.save(user);
        guildService.delete(user.getGuild());
    }

    public void leaveGuild(String authorization) throws Exception {
        User user = usersRetriever.retrieveUser(authorization);

        throwExceptionIf_GuildIsNotPresent(user);
        throwExceptionIf_UserIsGuildOwner(user);

        userService.removeChat(user.getId(), user.getGuild().getChat().getId());
        user.setRole(roleService.find("user"));
        user.setGuild(null);
        userService.save(user);
    }

    private Chat createChat(User user) {
        Chat chat = new Chat();
        chat.getAdmins().add(new Admin(ChatRole.OWNER, user.getLogin()));

        return chat;
    }

    private Guild createGuild(User user, CreateGuildInformer informer, Chat chat) {
        Guild guild = new Guild(informer.guildName);
        guild.setMinimumLevel(informer.minimumLevel);
        guild.addUser(user);
        guild.setChat(chat);

        return guild;
    }

    private void throwExceptionIf_UserIsNotGuildOwner(User user) throws NoAccessException {
        if (!user.getRole().getRole().equals(GuildRole.OWNER.getRole()))
            throw new NoAccessException("user have no access to do that");
    }

    private void throwExceptionIf_UserIsGuildOwner(User user) throws NoAccessException {
        if (user.getRole().getRole().equals(GuildRole.OWNER.getRole()))
            throw new NoAccessException("user have no access to do that");
    }

    private void throwExceptionIf_GuildNameIsNotPresent(CreateGuildInformer informer) throws Exception {
        if (informer.guildName == null)
            throw new Exception("Wrong guild name");
    }

    private void throwExceptionIf_GuildNameAlreadyExist(CreateGuildInformer informer) throws Exception {
        if (guildService.find(informer.guildName) != null)
            throw new Exception("Guild already exist");
    }

    private void throwExceptionIf_GuildIsNotPresent(User user) throws Exception {
        if (user.getGuild() == null)
            throw new Exception("You already have guild");
    }

    private void throwExceptionIf_GuildIsPresent(User user) throws Exception {
        if (user.getGuild() != null)
            throw new Exception("You already have guild");
    }
}
